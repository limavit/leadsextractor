package br.com.ephesos.scraperleads.dao.impl;

import br.com.ephesos.scraperleads.dao.RegistroDAO;
import br.com.ephesos.scraperleads.db.DB;
import br.com.ephesos.scraperleads.db.DbException;
import br.com.ephesos.scraperleads.model.Licenca;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class RegistroDAOJDBC implements RegistroDAO {
    private Connection conn;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    java.util.Date dataUtil = new java.util.Date();
    java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());


    public RegistroDAOJDBC(Connection conn) {
        super();
        this.conn = conn;
    }

    

    @Override
    public Date getVencimento(Licenca obj) {
        PreparedStatement st  = null;
        ResultSet rs = null;


        String sql = "SELECT vencimento FROM t_registro WHERE chave = ? AND docusuario = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getChave());
            st.setString(2, obj.getDocUsuario());
            rs = st.executeQuery();
            if(rs.next()){

                return rs.getDate("vencimento");
            }
            return  null;


        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }
}
