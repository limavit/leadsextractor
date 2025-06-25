package br.com.ephesos.scraperleads.model;

import java.time.LocalDate;

public class Licenca {
    private String docUsuario;
    private String chave;
    private LocalDate vencimento;
    private String acesso;

    public Licenca() {
    }

    public Licenca(String docUsuario, String chave, LocalDate vencimento, String acesso) {
        this.docUsuario = docUsuario;
        this.chave = chave;
        this.vencimento = vencimento;
        this.acesso = acesso;
    }

    public String getDocUsuario() {
        return docUsuario;
    }

    public void setDocUsuario(String docUsuario) {
        this.docUsuario = docUsuario;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

}
