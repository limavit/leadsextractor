package br.com.ephesos.scraperleads;

import br.com.ephesos.scraperleads.controller.ConsultaController;
import br.com.ephesos.scraperleads.controller.GeradorExcelController;
import br.com.ephesos.scraperleads.controller.RegistroController;
import br.com.ephesos.scraperleads.controller.SerialHDController;
import br.com.ephesos.scraperleads.dao.DAOFactory;
import br.com.ephesos.scraperleads.dao.SerialHDDAO;
import br.com.ephesos.scraperleads.model.Lead;
import br.com.ephesos.scraperleads.model.Licenca;
import br.com.ephesos.scraperleads.model.SerialHD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialStruct;
import java.util.Scanner;
import java.util.Set;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Verificação da licença

        System.out.println("Cadastrando dados unicos do computador... ");
        SerialHD serialHD = new SerialHD();
        SerialHDController serialHDController = new SerialHDController();
        serialHD.setDocumento("12345789");
        serialHD.setSerialHD(serialHDController.consultaSerialHD());
        // inserindo no banco
        SerialHDDAO serialHDDAO = DAOFactory.createSerialHDDAO();
        serialHDDAO.insert(serialHD);
        //Verificando licença
        System.out.println("Verificando licença: ");
        System.out.println("Insira a chave do produto: ");
        Licenca licenca = new Licenca();
        licenca.setDocUsuario("12345789");
        licenca.setChave("59ccf8a5-3353-11f0-920f-40b076226f4a");
        RegistroController registroController = new RegistroController();
        boolean reg = registroController.validaVencimento(licenca);
        //logica para proibir uso da aplicacao caso licença vencida

        //Avisos




        // Aplicaçao

        System.out.println("Informe a categoria: ");
        String categoria = new Scanner(System.in).nextLine();
        System.out.println("Informe a cidade: ");
        String cidade = new Scanner(System.in).nextLine();
        System.out.println("Informe a UF: ");
        String uf = new Scanner(System.in).nextLine();
        SerialHDController serialHd = new SerialHDController();

        ConsultaController consultaController = new ConsultaController();
        Set<Lead> leads = consultaController.consultaEmpresas(categoria, cidade, uf);







        //launch();
        System.out.println("Insira o caminho onde deseja salvar seu arquivo Excel: ");
        String caminho = new Scanner(System.in).nextLine();
        GeradorExcelController geradorExcelController = new GeradorExcelController();
        geradorExcelController.geraExcel(leads, caminho);
        System.exit(0);
    }


}
