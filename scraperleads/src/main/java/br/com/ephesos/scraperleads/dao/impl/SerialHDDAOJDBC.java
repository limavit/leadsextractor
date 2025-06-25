package br.com.ephesos.scraperleads.dao.impl;

import br.com.ephesos.scraperleads.dao.DAOFactory;
import br.com.ephesos.scraperleads.dao.SerialHDDAO;
import br.com.ephesos.scraperleads.db.DbException;
import br.com.ephesos.scraperleads.model.SerialHD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SerialHDDAOJDBC implements SerialHDDAO {
    private Connection conn;

    public SerialHDDAOJDBC(Connection conn){
        super();
        this.conn = conn;
    }

    @Override
    public void insert(SerialHD obj) {
        PreparedStatement st = null;

        String sql = "INSERT INTO t_serialhd(documento, serialhd, inserido) values(?, ?, current_timestamp())";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getDocumento());
            st.setString(2, obj.getSerialHD());
            st.executeUpdate();
            System.out.println("Registro do HD cadastrado na base de dados");
        }catch (SQLException e){
            throw new DbException(e.getMessage());

        }
    }
}
