package br.com.ephesos.scraperleads.model;

import java.util.Objects;

public class Lead {
    private String name;
    private String endereco;
    private String telefone;
    private String email;
    private String website;
    private String instagram;
    private String categoria;
    private String cidade;
    private String fonte;
    private String dataCaptura;

    public Lead() {
    }

    public Lead(String name, String endereco, String telefone, String email, String website, String instagram, String categoria, String cidade, String fonte, String dataCaptura) {
        this.name = name;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.website = website;
        this.instagram = instagram;
        this.categoria = categoria;
        this.cidade = cidade;
        this.fonte = fonte;
        this.dataCaptura = dataCaptura;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(String dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return Objects.equals(getName(), lead.getName()) && Objects.equals(getEndereco(), lead.getEndereco()) && Objects.equals(getTelefone(), lead.getTelefone()) && Objects.equals(getEmail(), lead.getEmail()) && Objects.equals(getWebsite(), lead.getWebsite()) && Objects.equals(getInstagram(), lead.getInstagram()) && Objects.equals(getCategoria(), lead.getCategoria()) && Objects.equals(getCidade(), lead.getCidade()) && Objects.equals(getFonte(), lead.getFonte()) && Objects.equals(getDataCaptura(), lead.getDataCaptura());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEndereco(), getTelefone(), getEmail(), getWebsite(), getInstagram(), getCategoria(), getCidade(), getFonte(), getDataCaptura());
    }

    @Override
    public String toString() {
        return "--- Empresa ---" +
                "\n Nome: " + name +
                "\n Endereco: " + endereco  +
                "\n Telefone: " + telefone +
                "\n Email: " + email  +
                "\n Website: " + website +
                "\n Instagram: " + instagram +
                "\n Categoria: " + categoria +
                "\n Cidade: " + cidade +
                "\n Fonte: " + fonte +
                "\n DataCaptura: " + dataCaptura;
    }
}
