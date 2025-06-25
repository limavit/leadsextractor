package br.com.ephesos.scraperleads.dao.impl;

import br.com.ephesos.scraperleads.dao.AvisoDAO;
import br.com.ephesos.scraperleads.model.Aviso;
import br.com.ephesos.scraperleads.model.Lead;

import java.sql.Connection;

public class AvisoDAOJDBC implements AvisoDAO {
    private Connection conn;
    private AvisoDAOJDBC(Connection conn){
        super();
        this.conn = conn;
    }


    @Override
    public Aviso retornAviso(Lead obj) {
        return null;
    }
}
