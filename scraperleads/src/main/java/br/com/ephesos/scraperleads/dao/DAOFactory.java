package br.com.ephesos.scraperleads.dao;

import br.com.ephesos.scraperleads.dao.impl.RegistroDAOJDBC;
import br.com.ephesos.scraperleads.dao.impl.SerialHDDAOJDBC;
import br.com.ephesos.scraperleads.db.DB;
import br.com.ephesos.scraperleads.model.Lead;

public class DAOFactory {
    public static RegistroDAO createRegistroDao() {
        return new RegistroDAOJDBC(DB.getConnection());
    }
    public static SerialHDDAO createSerialHDDAO() { return new SerialHDDAOJDBC(DB.getConnection()); }

}
