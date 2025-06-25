package br.com.ephesos.scraperleads.model;

import java.util.Objects;

public class SerialHD {
    private Integer id;
    private String documento;
    private String serialHD;

    public SerialHD(){

    }

    public SerialHD(Integer id, String documento, String serialHD) {
        this.id = id;
        this.documento = documento;
        serialHD = serialHD;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSerialHD() {
        return serialHD;
    }

    public void setSerialHD(String serialHD) {
        this.serialHD = serialHD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerialHD serialHD1 = (SerialHD) o;
        return Objects.equals(getId(), serialHD1.getId()) && Objects.equals(getDocumento(), serialHD1.getDocumento()) && Objects.equals(getSerialHD(), serialHD1.getSerialHD());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDocumento(), getSerialHD());
    }
}
