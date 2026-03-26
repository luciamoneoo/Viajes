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
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║       T R A V E L L O G  ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║  1. Ver todos los viajes ║");
        System.out.println("║  2. Añadir un viaje      ║");
        System.out.println("║  3. Eliminar un viaje    ║");
        System.out.println("║  0. Salir                ║");
        System.out.println("╚══════════════════════════╝");
    }

    private void mostrarInventario() {
        if (gestor.getListaViajes().isEmpty()) {
            System.out.println("No hay viajes registrados actualmente.");
            return;
        }
        
        // Uso de printf para tabular la salida (Requisito RA5)
        System.out.printf("%n%-8s %-15s %-15s %-10s %10s%n", 
            "ID", "TIPO", "DESTINO", "DÍAS", "PRECIO");
        System.out.println("─".repeat(62));
        
        for (Viajes v : gestor.getListaViajes()) {
            System.out.printf("%-8s %s%n", v.getId(), v.resumenViaje());
        }
        System.out.println("─".repeat(62));
    }

    private void crearViaje() {
        System.out.println("\n--- CREAR NUEVO VIAJE ---");
        
        // Uso de Regex para validar texto (Requisito RA6)
        String nombre = leerTextoRegex("Nombre del viaje (solo letras): ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,40}$");
        String destino = leerTextoRegex("Destino (solo letras): ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$");
        int nPersonas = leerEntero("Número de personas (1-50): ", 1, 50);
        double precioBase = leerDouble("Precio base por persona (€): ", 0.0, 10000.0);
        int dias = leerEntero("Duración en días (1-365): ", 1, 365);
        
        // Actividades (Array simple para cumplir RA6)
        System.out.print("Introduce una actividad principal: ");
        String[] actividades = new String[]{ sc.nextLine().trim() };

        int tipo = leerEntero("¿Es Nacional (1) o Internacional (2)? ", 1, 2);

        if (tipo == 1) {
            String comunidad = leerTextoRegex("Comunidad Autónoma: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$");
            ViajesNacionales vn = new ViajesNacionales(nombre, nPersonas, destino, precioBase, dias, actividades, comunidad);
            gestor.aniadirViaje(vn);
            System.out.println("¡Viaje nacional añadido con éxito!");
        } else {
            int visado = leerEntero("¿Requiere visado? (1 = Sí, 2 = No): ", 1, 2);
            ViajesInternacionales vi = new ViajesInternacionales(nombre, nPersonas, destino, precioBase, dias, actividades, visado == 1);
            gestor.aniadirViaje(vi);
            System.out.println("¡Viaje internacional añadido con éxito!");
        }
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

    // --- MÉTODOS AUXILIARES DE VALIDACIÓN ---

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
            sc.nextLine(); // Limpiar el buffer
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
            sc.nextLine(); // Limpiar buffer
        } while (!valido);
        return valor;
    }
}