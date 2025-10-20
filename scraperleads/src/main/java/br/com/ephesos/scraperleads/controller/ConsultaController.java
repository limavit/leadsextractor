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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ConsultaController {

    private static final String BASE_URL = "https://paginaamarela.com.br";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final int TIMEOUT_SECONDS = 30;
    private static final long DELAY_BETWEEN_REQUESTS_MS = 1000; // 1 segundo entre requisições
    private static final int MAX_PAGINAS = 100; // Limite de segurança
    private static final boolean DEBUG_MODE = true; // Ative para ver logs detalhados

    private final HttpClient client;

    public ConsultaController() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    public Set<Lead> consultaEmpresas(String categoria, String cidade, String uf) {
        Set<Lead> leads = new LinkedHashSet<>();
        Set<String> urlsVisitadas = new HashSet<>();
        int contadorEmpresa = 0;
        int pagina = 1;
        boolean continuar = true;

        System.out.println(String.format("Iniciando busca: %s em %s/%s", categoria, cidade, uf));

        while (continuar && pagina <= MAX_PAGINAS) {
            System.out.println("\n=== Página " + pagina + " ===");

            try {
                String url = construirUrlBusca(categoria, cidade, uf, pagina);
                Document doc = buscarPagina(url);

                if (doc == null) {
                    System.out.println("Erro ao carregar página. Finalizando.");
                    break;
                }

                Elements resultados = doc.select("a[href^=/empresa/]");
                Set<String> urlsPaginaAtual = extrairUrlsEmpresas(resultados);

                // IMPORTANTE: Verifica se a página tem empresas listadas
                if (urlsPaginaAtual.isEmpty()) {
                    System.out.println("Página vazia encontrada. Finalizando busca.");
                    continuar = false;
                    continue;
                }

                System.out.println("Total de empresas na página: " + urlsPaginaAtual.size());

                // Remove URLs já visitadas
                Set<String> urlsNovas = new LinkedHashSet<>(urlsPaginaAtual);
                urlsNovas.removeAll(urlsVisitadas);

                if (urlsNovas.isEmpty()) {
                    System.out.println("Todas as empresas desta página já foram processadas.");
                    System.out.println("Continuando para próxima página...");
                    pagina++;
                    continue;
                }

                System.out.println("Empresas novas para processar: " + urlsNovas.size());

                urlsPaginaAtual = urlsNovas;

                System.out.println("Encontradas " + urlsPaginaAtual.size() + " empresas novas");

                for (String urlEmpresa : urlsPaginaAtual) {
                    urlsVisitadas.add(urlEmpresa);
                    contadorEmpresa++;

                    System.out.println(String.format("(%d) Processando: %s", contadorEmpresa, urlEmpresa));

                    Lead lead = extrairDadosEmpresa(urlEmpresa, categoria, cidade);

                    if (lead != null) {
                        leads.add(lead);
                        System.out.println("  ✓ Lead capturado: " + lead.getName());
                    } else {
                        System.out.println("  ✗ Não foi possível extrair dados");
                    }

                    // Delay entre requisições para não sobrecarregar o servidor
                    Thread.sleep(DELAY_BETWEEN_REQUESTS_MS);
                }

                pagina++;

            } catch (InterruptedException e) {
                System.err.println("Processo interrompido: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Erro na página " + pagina + ": " + e.getMessage());
                continuar = false;
            }
        }

        System.out.println("\n=== Resumo ===");
        System.out.println("Total de empresas processadas: " + contadorEmpresa);
        System.out.println("Total de leads capturados: " + leads.size());

        return leads;
    }

    private String construirUrlBusca(String categoria, String cidade, String uf, int pagina) {
        return String.format("%s/empresas/%s/%s/%s?pagina=%d",
                BASE_URL, categoria, cidade, uf, pagina);
    }

    private Document buscarPagina(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", USER_AGENT)
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Erro HTTP " + response.statusCode() + " ao acessar: " + url);
                return null;
            }

            return Jsoup.parse(response.body());

        } catch (Exception e) {
            System.err.println("Erro ao buscar página: " + e.getMessage());
            return null;
        }
    }

    private Set<String> extrairUrlsEmpresas(Elements links) {
        Set<String> urls = new LinkedHashSet<>();

        for (Element link : links) {
            String href = link.attr("href");
            if (href.startsWith("/empresa/")) {
                urls.add(BASE_URL + href);
            }
        }

        return urls;
    }

    private Lead extrairDadosEmpresa(String urlEmpresa, String categoria, String cidadeBusca) {
        try {
            Document doc = buscarPagina(urlEmpresa);

            if (doc == null) {
                System.out.println("  ⚠ Página não carregou");
                return null;
            }

            Elements scripts = doc.select("script[type=application/ld+json]");

            if (scripts.isEmpty()) {
                System.out.println("  ⚠ Nenhum JSON LD encontrado na página");
                // Tentar extração alternativa via HTML direto
                return extrairDadosAlternativos(doc, categoria, cidadeBusca);
            }

            System.out.println("  → " + scripts.size() + " script(s) JSON encontrado(s)");

            for (int i = 0; i < scripts.size(); i++) {
                Element script = scripts.get(i);
                String scriptText = script.html().trim();

                try {
                    // Tentar corrigir problemas comuns no JSON
                    String jsonLimpo = limparJson(scriptText);

                    JSONObject json = new JSONObject(jsonLimpo);
                    String tipo = json.optString("@type", "");

                    System.out.println("    Script " + (i+1) + ": @type = " + tipo);

                    if ("LocalBusiness".equals(tipo)) {
                        Lead lead = criarLeadFromJson(json, categoria, cidadeBusca);
                        if (lead != null) {
                            return lead;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("    Script " + (i+1) + ": JSON inválido - " + e.getMessage());
                    // Tentar extrair dados do JSON malformado usando regex
                    Lead leadExtraido = extrairDadosDeJsonQuebrado(scriptText, categoria, cidadeBusca);
                    if (leadExtraido != null) {
                        System.out.println("    ✓ Dados recuperados do JSON malformado");
                        return leadExtraido;
                    }
                }
            }

            System.out.println("  ⚠ Nenhum LocalBusiness encontrado nos JSONs");
            // Tentar extração alternativa
            return extrairDadosAlternativos(doc, categoria, cidadeBusca);

        } catch (Exception e) {
            System.err.println("  ✗ Erro ao extrair dados: " + e.getMessage());
        }

        return null;
    }

    private Lead extrairDadosAlternativos(Document doc, String categoria, String cidadeBusca) {
        try {
            System.out.println("  → Tentando extração alternativa via HTML...");

            // Tentar extrair nome
            String nome = null;
            Element h1 = doc.selectFirst("h1");
            if (h1 != null) {
                nome = h1.text().trim();
            }

            // Tentar extrair telefone de múltiplas formas
            String telefone = null;

            // 1. Links tel:
            Elements linksTelefone = doc.select("a[href^=tel:]");
            if (!linksTelefone.isEmpty()) {
                telefone = linksTelefone.first().attr("href").replace("tel:", "").trim();
            }

            // 2. Elementos com classes/ids relacionados a telefone
            if (telefone == null) {
                Elements elemTel = doc.select("[class*=phone], [class*=telefone], [id*=phone], [id*=telefone], [itemprop=telephone]");
                if (!elemTel.isEmpty()) {
                    telefone = elemTel.first().text().trim();
                }
            }

            // 3. Buscar por padrão de telefone no HTML
            if (telefone == null) {
                String bodyText = doc.body().text();
                // Padrões: (11) 1234-5678, (11) 91234-5678, 11 1234-5678, etc
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                        "\\(?0?([1-9]{2})\\)?\\s*9?\\d{4}[-\\s]?\\d{4}"
                );
                java.util.regex.Matcher matcher = pattern.matcher(bodyText);
                if (matcher.find()) {
                    telefone = matcher.group().trim();
                }
            }

            // Tentar extrair endereço de múltiplas formas
            String endereco = null;

            // 1. Atributos semânticos
            Element enderecoElement = doc.selectFirst("[itemprop=address], [class*=address], [class*=endereco], [id*=address], [id*=endereco]");
            if (enderecoElement != null) {
                endereco = enderecoElement.text().trim();
            }

            // 2. Procurar por elementos que contenham CEP
            if (endereco == null) {
                Elements possiveisEnderecos = doc.select("p, div, span");
                java.util.regex.Pattern cepPattern = java.util.regex.Pattern.compile("\\d{5}-?\\d{3}");

                for (Element elem : possiveisEnderecos) {
                    String texto = elem.text();
                    if (cepPattern.matcher(texto).find() && texto.length() > 15 && texto.length() < 200) {
                        endereco = texto.trim();
                        break;
                    }
                }
            }

            // Se conseguiu pelo menos o nome, cria o lead
            if (nome != null && !nome.isEmpty()) {
                System.out.println("  ✓ Dados extraídos via HTML");
                if (telefone != null) System.out.println("    Tel: " + telefone);
                if (endereco != null) System.out.println("    End: " + (endereco.length() > 50 ? endereco.substring(0, 50) + "..." : endereco));

                return new Lead(
                        nome,
                        endereco != null ? endereco : "Endereço não informado",
                        telefone != null ? telefone : "Telefone não informado",
                        null,
                        null,
                        null,
                        categoria,
                        cidadeBusca,
                        null,
                        LocalDateTime.now().toString()
                );
            }

            System.out.println("  ⚠ Não foi possível extrair dados mínimos via HTML");

        } catch (Exception e) {
            System.out.println("  ✗ Erro na extração alternativa: " + e.getMessage());
        }

        return null;
    }

    private Lead criarLeadFromJson(JSONObject json, String categoria, String cidadeBusca) {
        try {
            String nome = json.optString("name", "").trim();

            // Validação: nome é obrigatório
            if (nome.isEmpty()) {
                System.out.println("    ⚠ JSON sem nome válido");
                return null;
            }

            String telefone = json.optString("telephone", "Telefone não informado");

            JSONObject address = json.optJSONObject("address");

            String rua = extrairCampoEndereco(address, "streetAddress", "");
            String cidadeEmpresa = extrairCampoEndereco(address, "addressLocality", cidadeBusca);
            String estado = extrairCampoEndereco(address, "addressRegion", "");
            String cep = extrairCampoEndereco(address, "postalCode", "");

            // Monta endereço apenas com campos não vazios
            List<String> partesEndereco = new ArrayList<>();
            if (!rua.isEmpty()) partesEndereco.add(rua);
            if (!cidadeEmpresa.isEmpty()) partesEndereco.add(cidadeEmpresa);
            if (!estado.isEmpty()) partesEndereco.add(estado);
            if (!cep.isEmpty()) partesEndereco.add(cep);

            String enderecoCompleto = partesEndereco.isEmpty()
                    ? "Endereço não informado"
                    : String.join(", ", partesEndereco);

            System.out.println("    ✓ Lead criado: " + nome);

            return new Lead(
                    nome,
                    enderecoCompleto,
                    telefone,
                    null,
                    null,
                    null,
                    categoria,
                    cidadeEmpresa,
                    null,
                    LocalDateTime.now().toString()
            );
        } catch (Exception e) {
            System.out.println("    ✗ Erro ao criar lead: " + e.getMessage());
            return null;
        }
    }

    private String extrairCampoEndereco(JSONObject address, String campo, String valorPadrao) {
        return address != null ? address.optString(campo, valorPadrao) : valorPadrao;
    }

    private void debug(String mensagem) {
        if (DEBUG_MODE) {
            System.out.println("[DEBUG] " + mensagem);
        }
    }

    private String limparJson(String json) {
        // Remove caracteres de controle problemáticos
        return json.replaceAll("[\\x00-\\x1F\\x7F]", "")
                .replace("\n", " ")
                .replace("\r", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private Lead extrairDadosDeJsonQuebrado(String jsonText, String categoria, String cidadeBusca) {
        try {
            // Tentar extrair campos específicos usando regex do JSON malformado
            String nome = extrairCampoRegex(jsonText, "\"name\"\\s*:\\s*\"([^\"]+)\"");
            String telefone = extrairCampoRegex(jsonText, "\"telephone\"\\s*:\\s*\"([^\"]+)\"");

            // Endereço pode estar dentro de "address"
            String rua = extrairCampoRegex(jsonText, "\"streetAddress\"\\s*:\\s*\"([^\"]+)\"");
            String cidade = extrairCampoRegex(jsonText, "\"addressLocality\"\\s*:\\s*\"([^\"]+)\"");
            String estado = extrairCampoRegex(jsonText, "\"addressRegion\"\\s*:\\s*\"([^\"]+)\"");
            String cep = extrairCampoRegex(jsonText, "\"postalCode\"\\s*:\\s*\"([^\"]+)\"");

            if (nome != null && !nome.isEmpty()) {
                List<String> partesEndereco = new ArrayList<>();
                if (rua != null) partesEndereco.add(rua);
                if (cidade != null) partesEndereco.add(cidade);
                if (estado != null) partesEndereco.add(estado);
                if (cep != null) partesEndereco.add(cep);

                String enderecoCompleto = partesEndereco.isEmpty()
                        ? "Endereço não informado"
                        : String.join(", ", partesEndereco);

                return new Lead(
                        nome,
                        enderecoCompleto,
                        telefone != null ? telefone : "Telefone não informado",
                        null,
                        null,
                        null,
                        categoria,
                        cidade != null ? cidade : cidadeBusca,
                        null,
                        LocalDateTime.now().toString()
                );
            }
        } catch (Exception e) {
            // Silencioso - vai tentar extração HTML depois
        }

        return null;
    }

    private String extrairCampoRegex(String texto, String pattern) {
        try {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(texto);
            if (m.find()) {
                return m.group(1).trim();
            }
        } catch (Exception e) {
            // Ignora erros
        }
        return null;
    }
}