package br.com.ephesos.scraperleads.controller;

import br.com.ephesos.scraperleads.model.Lead;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsultaController {
    public Set<Lead> consultaEmpresas(String categoria, String cidade, String uf){

        Set<Lead> leads = new HashSet<>();
        Integer contadorEmpresa = 0;


        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            int pagina = 1;

            boolean continuar = true;

            // Guarda as URLs já visitadas para evitar repetições
            Set<String> urlsVisitadas = new HashSet<>();

            while (continuar) {
                System.out.println("Pagina :" + pagina);

                String query = categoria + "/" + cidade + "/" + uf + "?pagina=" + pagina;
                String url = "https://paginaamarela.com.br/empresas/" + query;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("User-Agent", "Mozilla/5.0")
                        .GET()
                        .build();

                System.out.println("Acessando página: " + url);
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.out.println("Parando, pois a resposta HTTP foi " + response.statusCode());
                    break;
                }

                String html = response.body();
                Document doc = Jsoup.parse(html);
                Elements resultados = doc.select("a[href^=/empresa/]");

                boolean encontrouEmpresa = false;
                Set<String> urlsPaginaAtual = new HashSet<>();

                for (Element link : resultados) {
                    String href = link.attr("href");

                    if (href.startsWith("/empresa/")) {
                        String urlEmpresa = "https://paginaamarela.com.br" + href;

                        // Evita repetir empresa
                        if (urlsVisitadas.contains(urlEmpresa)) {
                            continue;
                        }

                        urlsPaginaAtual.add(urlEmpresa);
                        urlsVisitadas.add(urlEmpresa);
                        encontrouEmpresa = true;
                        contadorEmpresa++;

                        System.out.println("\nAcessando empresa: " + urlEmpresa);
                        System.out.println("Empresa de n: " + contadorEmpresa);

                        HttpRequest requestEmpresa = HttpRequest.newBuilder()
                                .uri(URI.create(urlEmpresa))
                                .header("User-Agent", "Mozilla/5.0")
                                .GET()
                                .build();

                        HttpResponse<String> responseEmpresa = client.send(requestEmpresa, HttpResponse.BodyHandlers.ofString());

                        if (responseEmpresa.statusCode() != 200) {
                            System.out.println("Erro ao acessar empresa: " + responseEmpresa.statusCode());
                            continue;
                        }

                        String htmlEmpresa = responseEmpresa.body();
                        Document docEmpresa = Jsoup.parse(htmlEmpresa);
                        Elements scripts = docEmpresa.select("script[type=application/ld+json]");

                        for (Element script : scripts) {
                            String scriptText = script.html().trim();

                            try {
                                JSONObject json = new JSONObject(scriptText);

                                if (json.has("@type") && json.getString("@type").equals("LocalBusiness")) {
                                    String nome = json.optString("name", "Nome não informado");
                                    String telefone = json.optString("telephone", "Telefone não informado");

                                    JSONObject address = json.optJSONObject("address");

                                    String rua = address != null ? address.optString("streetAddress", "Endereço não informado") : "Endereço não informado";
                                    String cidadeEmpresa = address != null ? address.optString("addressLocality", "Cidade não informada") : "Cidade não informada";
                                    String estado = address != null ? address.optString("addressRegion", "UF não informada") : "UF não informada";
                                    String cep = address != null ? address.optString("postalCode", "CEP não informado") : "CEP não informado";

                                    LocalDateTime dataInsercao;
                                    Lead lead = new Lead(nome, rua + ", " + cidadeEmpresa + ", " + estado + ", " + cep, telefone, null, null, null, categoria, cidadeEmpresa, null, LocalDateTime.now().toString());
                                    leads.add(lead);
                                    //String txtLead = lead.toString();
                                    // System.out.println(txtLead);

                                    break;
                                }

                            } catch (Exception e) {
                                // Ignora JSON inválido
                            }
                        }
                    }
                }
                for (Lead lead : leads) {
                    System.out.println(lead.toString());

                }

                if (!encontrouEmpresa || urlsPaginaAtual.isEmpty()) {
                    System.out.println("Nenhuma empresa nova na página " + pagina + ". Finalizando.");
                    continuar = false;
                } else {
                    pagina++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Ordenando

        return leads.stream()
                .sorted(Comparator.comparing(Lead::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
