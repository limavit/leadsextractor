package br.com.ephesos.scraperleads.controller;

import br.com.ephesos.scraperleads.model.Lead;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Set;

public class GeradorExcelController {

    public void geraExcel(Set<Lead> leads, String caminhoArquivo){

        System.out.println("Gerando Excel no caminho: " + caminhoArquivo);
        //planilha
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Leads");
        //cabeçalho
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nome");
        headerRow.createCell(1).setCellValue("Endereço");
        headerRow.createCell(2).setCellValue("Telefone");
        headerRow.createCell(3).setCellValue("Categoria");
        headerRow.createCell(4).setCellValue("Cidade");
        headerRow.createCell(5).setCellValue("DataCaptura");

        //dados
        int rowNum = 1;
        Lead lead = new Lead();
        for(Lead l : leads){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(l.getName());
            row.createCell(1).setCellValue(l.getEndereco());
            row.createCell(2).setCellValue(l.getTelefone());
            row.createCell(3).setCellValue(l.getCategoria());
            row.createCell(4).setCellValue(l.getCidade());
            row.createCell(5).setCellValue(l.getDataCaptura());
        }

        //ajuste das colunas
        for (int i = 0; i <=5; i++){
            sheet.autoSizeColumn(i);
        }
        try{
            //salvar o arquivo
            FileOutputStream fileOut = new FileOutputStream(caminhoArquivo);
            workbook.write(fileOut);
            System.out.println("Planilha  gerada com sucesso! Caminho: " + caminhoArquivo);

        }catch (Exception e){
            System.out.println("Erro gerado: " + e.getMessage());
        }

    }
}
