package com.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViajesGuardados implements Exportable {

    private static final String RutaCSV = "datos/viajes.csv";
    private static final String RutaJSON = "datos/viajes.json";

    @Override
    public void toCSV(List<Viajes> lista) {
        try (FileWriter fw = new FileWriter(RutaCSV)) {
            for (Viajes v : lista) {
                String tipo;
                if (v instanceof ViajesInternacionales) {
                    tipo = "INT";
                } else {
                    tipo = "NAC";
                }
                fw.write(tipo + ","
                    + v.getNombre()      + ","
                    + v.nPersonas()      + ","
                    + v.getDestino()     + ","
                    + v.getPrecio()      + ","
                    + v.getDias()        + ","
                    + v.getTransporte()  + ","
                    + v.getAlojamiento() + "\n");
            }
            System.out.println("Guardado en CSV");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<Viajes> fromCSV() {
        List<Viajes> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RutaCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length < 8) continue;

                String tipo        = partes[0];
                String nombre      = partes[1];
                int nPersonas      = Integer.parseInt(partes[2]);
                String destino     = partes[3];
                double precio      = Double.parseDouble(partes[4]);
                int dias           = Integer.parseInt(partes[5]);
                String transporte  = partes[6];
                String alojamiento = partes[7];

                Viajes v;
                if (tipo.equals("INT")) {
                    v = new ViajesInternacionales(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
                } else {
                    v = new ViajesNacionales(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
                }
                lista.add(v);
            }
        } catch (IOException e) {
            System.out.println("No se encontró fichero, empezando vacío.");
        }
        return lista;
    }
    public void toJSON(List<Viajes> lista) {
    try (FileWriter fw = new FileWriter(RutaJSON)) {
        fw.write("[\n");
        for (int i = 0; i < lista.size(); i++) {
            Viajes v = lista.get(i);
            String tipo;
            if (v instanceof ViajesInternacionales) {
                tipo = "Internacional";
            } else {
                tipo = "Nacional";
            }
            fw.write("  {\n");
            fw.write("    \"id\": \""         + v.getId()          + "\",\n");
            fw.write("    \"tipo\": \""        + tipo               + "\",\n");
            fw.write("    \"nombre\": \""      + v.getNombre()      + "\",\n");
            fw.write("    \"destino\": \""     + v.getDestino()     + "\",\n");
            fw.write("    \"personas\": "      + v.nPersonas()      + ",\n");
            fw.write("    \"precioBase\": "    + v.getPrecio()      + ",\n");
            fw.write("    \"precioFinal\": "   + v.calcularPrecio() + ",\n");
            fw.write("    \"dias\": "          + v.getDias()        + ",\n");
            fw.write("    \"transporte\": \""  + v.getTransporte()  + "\",\n");
            fw.write("    \"alojamiento\": \"" + v.getAlojamiento() + "\"\n");
            if (i < lista.size() - 1) {
                fw.write("  },\n");
            } else {
                fw.write("  }\n");
            }
        }
        fw.write("]\n");
        System.out.println("Exportado a JSON");
    } catch (IOException e) {
        System.out.println("Error al exportar JSON: " + e.getMessage());
    }
}
}