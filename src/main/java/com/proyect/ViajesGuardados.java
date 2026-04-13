//implementa Exportable y ee y escribe los ficheros CSV y JSON

package com.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// esta clase se encarga de guardar y cargar los viajes desde archivos CSV y JSON


public class ViajesGuardados implements Exportable {

    // rutas de los archivos donde se guardarán los viajes en formato CSV y JSON
    private static final String RutaCSV = "datos/viajes.csv";
    private static final String RutaJSON = "datos/viajes.json";

    // implementamos el método toCSV para guardar la lista de viajes en un archivo CSV, uno por linea
    // cada línea tiene el siguiente formato: tipo, nombre, personas, destino, precio, dias, transporte, alojamiento
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

    // implementamos el método fromCSV para cargar la lista de viajes desde un archivo CSV, leyendo cada línea y creando un objeto Viajes correspondiente en la memoria
    // cada línea se divide por comas y se crea el objeto correspondiente
    @Override
    public List<Viajes> fromCSV() {
        List<Viajes> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RutaCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; // aqui se ignoran las lineas vacias
                String[] partes = linea.split(",");
                if (partes.length < 8) continue; // aqui se ignoran las lineas incompletas

                // aqui se lee cada parte de la línea y se convierte al tipo adecuado para crear el objeto Viajes
                String tipo        = partes[0];
                String nombre      = partes[1];
                int nPersonas      = Integer.parseInt(partes[2]);
                String destino     = partes[3];
                double precio      = Double.parseDouble(partes[4]);
                int dias           = Integer.parseInt(partes[5]);
                String transporte  = partes[6];
                String alojamiento = partes[7];

                // aqui se crea el objeto Viajes correspondiente según el tipo leído (nacional o internacional) y lo añadimos a la lista
                Viajes v;
                if (tipo.equals("INT")) {
                    v = new ViajesInternacionales(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
                } else {
                    v = new ViajesNacionales(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
                }
                lista.add(v);
            }
        } catch (IOException e) { // si no se encuentra el archivo, se muestra un mensaje y se devuelve una lista vacía
            System.out.println("No se encontró fichero, empezando vacío.");
        }
        return lista;
    }

    //aqui exporta todos los viajes en formato JSON que esmás legible que CSV y muy útil para compartir datos
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

            // aqui se escribe cada viaje en formato JSON, con sus atributos y valores, y se separan por comas excepto el último
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