package br.com.ephesos.scraperleads.model;

import java.time.LocalDate;

public class Aviso {
    private int id;
    private String titulo;
    private String mensagem;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;

    public Aviso() {
    }

    public Aviso(int id, String titulo, String mensagem, LocalDate dataInicio, LocalDate dataFim, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

