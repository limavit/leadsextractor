package br.com.ephesos.scraperleads.controller;

import br.com.ephesos.scraperleads.dao.DAOFactory;
import br.com.ephesos.scraperleads.dao.RegistroDAO;
import br.com.ephesos.scraperleads.model.Licenca;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class RegistroController {
    RegistroDAO registroDAO = DAOFactory.createRegistroDao();

    public boolean validaVencimento(Licenca obj){

        LocalDate agora = LocalDate.now();
        Date vencimentoDb = registroDAO.getVencimento(obj);
        LocalDate vencimento = toLocalDate(vencimentoDb);
        System.out.println("Vencimento: " + vencimento);
        try {
            if (vencimentoDb != null) {
                mensagemLicenca("Validando licença...");
                if (vencimento.isBefore(agora)) {
                    mensagemLicenca("Licença expirada");
                    return false;
                }
                if (vencimento.isAfter(agora)) {
                    mensagemLicenca("Licença ativa!");
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



        return false;
    }
    public void mensagemLicenca(String msg){
        System.out.println(msg);
    }
    public LocalDate toLocalDate(Date date) {
        if (date == null) return null;

        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }
}
