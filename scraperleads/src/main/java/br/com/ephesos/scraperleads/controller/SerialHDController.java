package br.com.ephesos.scraperleads.controller;

import org.apache.poi.ss.extractor.ExcelExtractor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SerialHDController {
    public String consultaSerialHD(){
        String serialHd = "";

        try {
            Process process = Runtime.getRuntime().exec("wmic diskdrive get serialnumber");
            BufferedReader reader = new BufferedReader((new InputStreamReader(process.getInputStream())));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.toLowerCase().contains("serialnumber")) {
                    serialHd += line.trim();
                }
            }
        }catch (Exception e){
            System.out.println("Erro de execução: " + e.getMessage());
        }

        return serialHd;
    }
}
