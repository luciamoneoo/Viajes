package com.proyect;

import javax.swing.SwingUtilities;

public class AppGUI {
    // es el punto de entrada al ejecutar, uno arranca la consola que seria app ,y otro la ventana que sería appGUI
    public static void main(String[] args) {
        GestorViajes gestor = new GestorViajes();
        ViajesGuardados guardado = new ViajesGuardados();

        for (Viajes v : guardado.fromCSV()) {
            // añadimos al gestor cada viaje que nos devuelve el método fromCSV del guardado
            //  que lee el fichero y lo convierte en objetos Viajes
            gestor.añadirViaje(v);
        }

        // SwingUtilities.invokeLater es la forma correcta de arrancar una GUI en Java
        SwingUtilities.invokeLater(() -> {
            Menu ventana = new Menu(gestor, guardado);
            ventana.setVisible(true);
        });
    }
}