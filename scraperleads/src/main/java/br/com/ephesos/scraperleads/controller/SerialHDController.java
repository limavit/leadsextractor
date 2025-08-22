package br.com.ephesos.scraperleads.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SerialHDController {
    public String consultaSerialHDWindows(){
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
    public String consultaSerialHDLinux() {
        try {
            // Método mais confiável para a maioria das distribuições
            Process process = Runtime.getRuntime().exec(new String[]{
                    "sh", "-c", "for disk in /dev/sd? /dev/nvme?; do [ -b \"$disk\" ] && echo \"$disk: $(udevadm info --query=property --name=$disk | grep ID_SERIAL= | cut -d'=' -f2)\"; done | head -n1 | awk '{print $2}'"
            });

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String serial = reader.readLine();

            if (serial != null && !serial.trim().isEmpty()) {
                return serial.trim();
            }

        } catch (Exception e) {
            System.out.println("Erro com udevadm: " + e.getMessage());
        }

        return "Serial-Não-Encontrado";
    }

}
