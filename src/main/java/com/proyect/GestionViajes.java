// para la terminal

package com.proyect;

import java.util.Scanner;

public class GestionViajes {

    // atributos que necesito para gestionar los viajes y guardarlos
    private GestorViajes gestor; // gestiona los viajes en memoria
    private ViajesGuardados guardado; // guarda/carga los viajes en fichero
    private Scanner sc = new Scanner(System.in); // lee lo que escribe el usuario
 

    public GestionViajes(GestorViajes gestor, ViajesGuardados guardado) {
        this.gestor = gestor;
        this.guardado = guardado;
        
    }

    public void iniciar() {
         // arranca el bucle principal del menú. Se repite hasta que el usuario elige la opción 0

        int opcion = -1;
        while (opcion != 0) {
            System.out.println(" AGENCIA DE VIAJES ");
            System.out.println("1. Ver mis viajes");
            System.out.println("2. Añadir viaje");
            System.out.println("3. Eliminar viaje");
            System.out.println("4. Guardar viajes");
            System.out.println("0. Salir");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Tienes que poner un numero");
                continue;  // volvemos al inicio del while si el usuario no puso un número
            }

            if (opcion == 1) {
                mostrarViajes();
            } else if (opcion == 2) {
                añadirViaje();
            } else if (opcion == 3) {
                eliminarViaje();
            }   else if (opcion == 4) {
                guardarFichero();
            }else if (opcion != 0) {
                System.out.println("No existe esa opcion");
            }
        }
        System.out.println("Chao pescao");
    }

    // muestra todos los viajes en una tabla formateada por columnas
    // si no hay viajes, avisa al usuario
    private void mostrarViajes() {
        if (gestor.getListaViajes().isEmpty()) {
            System.out.println("No hay ningún viaje todavía");
            return;
        }

        //printf para alinear las columnas
        System.out.printf("%-6s %-20s %-15s %-15s %-14s %-8s %5s%n", "ID", "NOMBRE", "DESTINO", "TRANSPORTE", "ALOJAMIENTO", "DÍAS", "PRECIO");
        for (Viajes v : gestor.getListaViajes()) {
            System.out.printf("%-6s %-20s %-15s %-15s %-15s %-6s %7.2feur%n",
                v.getId(), v.getNombre(), v.getDestino(), v.getTransporte(), v.getAlojamiento(), v.getDias(), v.calcularPrecio() );
        }
    }

    private void añadirViaje() {
    // pide al usuario todos los datos del nuevo viaje con validaciones
    // y lo añade al gestor en memoria

        System.out.println("Nuevo viaje");

         // pedirTexto() valida que el texto cumpla el formato
        String nombre = pedirTexto("Nombre: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,40}$");
        String destino = pedirTexto("Destino: ", "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$");

        int personas = pedirEntero("Nº personas (1-50): ", 1, 50);
        double precio = pedirDecimal("Precio base : ", 0, 10000 );
        int dias = pedirEntero("Días de viaje: ", 1, 365);

        String transporte = pedirTexto("Transporte (avion, tren, bus...): ", ".+");
        String alojamiento = pedirTexto("Alojamiento (hotel, hostal...): ", ".+");

        System.out.print("Si es nacional pulse 1, si es internacional pulse 2 ");
        int tipo = pedirEntero("", 1, 2);

        // creamos el tipo correcto según la elección del usuario
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
         // pide el ID del viaje a eliminar y llama al gestor para borrarlo
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
        // valida que el texto introducido cumpla la expresión regular
        // si no cumple, pide al usuario que lo vuelva a escribir

        String input = "";
        while (!input.matches(regex)) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.matches(regex)) System.out.println("Formato incorrecto");
        }
        return input;
    }

    private int pedirEntero(String prompt, int min, int max) {
        // valida que el número entero esté dentro del maximo y minimo puesto
        //sino de nuevo se l pide al usuario que lo repita

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
        // valida que el número decimal esté dentro del rango 

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
    private void guardarFichero() {
        // preguntamos al usuario si quiere guardar en CSV o JSON y llama al método que toca

    System.out.print("¿Guardar en CSV o JSON? (csv/json): ");
    String opcion = sc.nextLine().trim().toLowerCase();

    if (opcion.equals("csv")) {
       guardado.toCSV(gestor.getListaViajes());  // <- le pasas la lista
        System.out.println("Guardado en CSV");
    } else if (opcion.equals("json")) {
        guardado.toJSON(gestor.getListaViajes());
        System.out.println("Guardado en JSON");
    } else {
        System.out.println("Opción no válida, pon csv o json");
    }
}
}