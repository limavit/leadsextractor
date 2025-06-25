package br.com.ephesos.scraperleads.dao;

import br.com.ephesos.scraperleads.model.Aviso;
import br.com.ephesos.scraperleads.model.Lead;

public interface AvisoDAO {
    Aviso retornAviso(Lead obj);

}
