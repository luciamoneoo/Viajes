package com.proyect;

import java.util.Scanner;

public class GestionViajes {

    private final GestorViajes gestor;
    private final Scanner sc = new Scanner(System.in);

    // Le pasamos el gestor por constructor para que trabaje sobre la misma lista
    public GestionViajes(GestorViajes gestor) {
        this.gestor = gestor;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ", 0, 3);
            switch (opcion) {
                case 1 -> mostrarInventario();
                case 2 -> crearViaje();
                case 3 -> borrarViaje();
                case 0 -> System.out.println("Guardando y cerrando el sistema...");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
       
        System.out.println("1. Ver todos los viajes");
        System.out.println("2. Añadir un viaje");
        System.out.println("3. Eliminar un viaje");
        System.out.println("0. Salir");
    }

    private void mostrarInventario() {
        if (gestor.getListaViajes().isEmpty()) {
            System.out.println("No hay viajes registrados");
            return;
        }
        
        System.out.printf("%n%-8s %-15s %-15s %-10s %10s%n", 
            "ID", "TIPO", "DESTINO", "DÍAS", "PRECIO");
        System.out.println("─".repeat(50));
        
        for (Viajes v : gestor.getListaViajes()) {
            System.out.printf("%-15s %s%n", v.getId(), v.resumenViaje());
        }
        System.out.println("─".repeat(50));
    }

    private void crearViaje() {
    System.out.println(" CREAR NUEVO VIAJE ");

    String nombre = leerTextoRegex("Nombre del viaje: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,40}$");
    String destino = leerTextoRegex("Destino: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$");
    int nPersonas = leerEntero("Número de personas: ", 1, 50);
    double precioBase = leerDouble("Precio base (€): ", 0.0, 10000.0);
    int dias = leerEntero("Días: ", 1, 365);

    String transporte = leerTextoRegex("Transporte: ", ".*");
    String alojamiento = leerTextoRegex("Alojamiento: ", ".*");

    int tipo = leerEntero("¿Nacional (1) o Internacional (2)? ", 1, 2);

    Viajes viaje;

    if (tipo == 1) {
        viaje = new ViajesNacionales(nombre, nPersonas, destino, precioBase, dias, transporte, alojamiento);
    } else {
        viaje = new ViajesInternacionales(nombre, nPersonas, destino, precioBase, dias, transporte, alojamiento);
    }

    gestor.añadirViaje(viaje);
    System.out.println("¡Viaje añadido correctamente!");
}

    private void borrarViaje() {
        System.out.print("\nIntroduce el ID del viaje a eliminar: ");
        String id = sc.nextLine().trim();
        
        if (gestor.eliminarViaje(id)) {
            System.out.println("Viaje eliminado correctamente de los registros.");
        } else {
            System.out.println("Error: No se ha encontrado ningún viaje con el ID " + id);
        }
    }

    private String leerTextoRegex(String prompt, String regex) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.matches(regex)) {
                System.out.println("  -> Formato incorrecto. Por favor, revisa el texto.");
            }
        } while (!input.matches(regex));
        return input;
    }

    private int leerEntero(String prompt, int min, int max) {
        int valor = -1;
        boolean valido = false;
        do {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.println("  -> El número debe estar entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("  -> Por favor, introduce un número válido.");
            }
            sc.nextLine(); 
        } while (!valido);
        return valor;
    }

    private double leerDouble(String prompt, double min, double max) {
        double valor = -1.0;
        boolean valido = false;
        do {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                valor = sc.nextDouble();
                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.println("  -> El precio debe estar entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("  -> Por favor, introduce un importe válido (usa coma para decimales).");
            }
            sc.nextLine(); 
        } while (!valido);
        return valor;
    }
}