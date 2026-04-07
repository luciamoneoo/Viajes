package com.proyect;

import java.util.Scanner;

public class GestionViajes {

    private GestorViajes gestor;
    private Scanner sc = new Scanner(System.in);

    public GestionViajes(GestorViajes gestor) {
        this.gestor = gestor;
        
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println(" AGENCIA DE VIAJES ");
            System.out.println("1. Ver mis viajes");
            System.out.println("2. Añadir viaje");
            System.out.println("3. Eliminar viaje");
            System.out.println("0. Salir");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Tienes que poner un numero");
                continue;
            }

            if (opcion == 1) {
                mostrarViajes();
            } else if (opcion == 2) {
                añadirViaje();
            } else if (opcion == 3) {
                eliminarViaje();
            } else if (opcion != 0) {
                System.out.println("No existe esa opcion");
            }
        }
        System.out.println("Chao pescao");
    }

    private void mostrarViajes() {
        if (gestor.getListaViajes().isEmpty()) {
            System.out.println("No hay ningún viaje todavía");
            return;
        }
        System.out.printf("%-6s %-20s %-15s %-17s %-12s %-8s %8s%n", "ID", "NOMBRE", "DESTINO", "TRANSPORTE", "ALOJAMIENTO", "DÍAS", "PRECIO");
        for (Viajes v : gestor.getListaViajes()) {
            System.out.printf("%-6s %-20s %-15s %-15s %-15s %-2s %7.2feur%n",
                v.getId(), v.getNombre(), v.getDestino(), v.getTransporte(), v.getAlojamiento(), v.getDias(), v.calcularPrecio() );
        }
    }

    private void añadirViaje() {
        System.out.println("Nuevo viaje");

        String nombre = pedirTexto("Nombre: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,40}$");
        String destino = pedirTexto("Destino: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$");

        int personas = pedirEntero("Nº personas (1-50): ", 1, 50);
        double precio = pedirDecimal("Precio base : ", 0, 10000 );
        int dias = pedirEntero("Días de viaje: ", 1, 365);

        String transporte = pedirTexto("Transporte (avion, tren, bus...): ", ".+");
        String alojamiento = pedirTexto("Alojamiento (hotel, hostal...): ", ".+");

        System.out.print("Si es nacional pulse 1, si es internacional pulse 2 ");
        int tipo = pedirEntero("", 1, 2);

        Viajes v;
        if (tipo == 1) {
            v = new ViajesNacionales(nombre, personas, destino, precio, dias, transporte, alojamiento);
        } else {
            v = new ViajesInternacionales(nombre, personas, destino, precio, dias, transporte, alojamiento);
        }

        gestor.añadirViaje(v);
        System.out.println("ID de tu viaje : " + v.getId());
    }

    private void eliminarViaje() {
        System.out.print("ID de tu viaje eliminado: ");
        String id = sc.nextLine().trim();

        boolean ok = gestor.eliminarViaje(id);
        if (ok) {
            System.out.println("Viaje eliminado");
        } else {
            System.out.println("Ningun viaje contiene ese ID");
        }
    }

    private String pedirTexto(String prompt, String regex) {
        String input = "";
        while (!input.matches(regex)) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.matches(regex)) System.out.println("Formato incorrecto");
        }
        return input;
    }

    private int pedirEntero(String prompt, int min, int max) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) return n;
                System.out.println(" Tiene que ser entre " + min + " y " + max );
            } catch (NumberFormatException e) {
                System.out.println(" Tiene que ser un numero");
            }
        }
    }

    private double pedirDecimal(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double d = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                if (d >= min && d <= max) return d;
                System.out.println("  Tiene que ser entre " + min + " y " + max );
            } catch (NumberFormatException e) {
                System.out.println(" Ese numero es incorrecto");
            }
        }
    }
}