package br.com.ephesos.scraperleads.dao.impl;

import br.com.ephesos.scraperleads.dao.AvisoDAO;
import br.com.ephesos.scraperleads.model.Aviso;
import br.com.ephesos.scraperleads.model.Lead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvisoDAOJDBC implements AvisoDAO {
    private Connection conn;
    private AvisoDAOJDBC(Connection conn){
        super();
        this.conn = conn;
    }


    @Override
    public Aviso retornAviso(String documento) {

        PreparedStatement st  = null;
        ResultSet rs = null;
        String sql = "SELECT titulo, mensagem FROM t_aviso WHERE id = (SELECT id_aviso FROM t_usuario_aviso WHERE documento_usuario = ? AND ocultado = 0) AND ativo = 1";
        Aviso aviso = new Aviso();

        try {
            st = conn.prepareStatement(sql);
            st.setString(1,documento);
            rs = st.executeQuery();
            String titulo = rs.getString("titulo");
            String mensagem = rs.getString("mensagem");
            aviso.setTitulo(titulo);
            aviso.setMensagem(mensagem);



        }catch (SQLException e){
            e.printStackTrace();
        }


        return null;
    }
}
