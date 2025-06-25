package br.com.ephesos.scraperleads.model;

import java.time.LocalDateTime;

public class UsuarioAviso {
    private int id;
    private int idAviso;
    private String documentoUsuario;
    private boolean ocultado;
    private LocalDateTime visualizadoEm;

    public UsuarioAviso() {
    }

    public UsuarioAviso(int id, int idAviso, String documentoUsuario, boolean ocultado, LocalDateTime visualizadoEm) {
        this.id = id;
        this.idAviso = idAviso;
        this.documentoUsuario = documentoUsuario;
        this.ocultado = ocultado;
        this.visualizadoEm = visualizadoEm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(int idAviso) {
        this.idAviso = idAviso;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public boolean isOcultado() {
        return ocultado;
    }

    public void setOcultado(boolean ocultado) {
        this.ocultado = ocultado;
    }

    public LocalDateTime getVisualizadoEm() {
        return visualizadoEm;
    }

    public void setVisualizadoEm(LocalDateTime visualizadoEm) {
        this.visualizadoEm = visualizadoEm;
    }
}

