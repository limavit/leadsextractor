package br.com.ephesos.scraperleads.dao;

import br.com.ephesos.scraperleads.model.Licenca;

import java.util.Date;

public interface RegistroDAO {
    Date getVencimento(Licenca obj);
}
