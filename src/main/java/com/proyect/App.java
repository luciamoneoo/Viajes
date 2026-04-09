package com.proyect;



public class App {
    public static void main(String[] args) {
        GestorViajes gestor = new GestorViajes();
        ViajesGuardados guardado = new ViajesGuardados();

        for (Viajes v : guardado.fromCSV()) {
            gestor.añadirViaje(v);
        }
        GestionViajes consola = new GestionViajes(gestor, guardado);

        consola.iniciar();

        
}
}
