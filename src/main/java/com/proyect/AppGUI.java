package com.proyect;

import javax.swing.SwingUtilities;

public class AppGUI {
    public static void main(String[] args) {
        GestorViajes gestor = new GestorViajes();
        ViajesGuardados guardado = new ViajesGuardados();

        for (Viajes v : guardado.fromCSV()) {
            gestor.añadirViaje(v);
        }

        // SwingUtilities.invokeLater es la forma correcta de arrancar una GUI en Java
        SwingUtilities.invokeLater(() -> {
            Menu ventana = new Menu(gestor, guardado);
            ventana.setVisible(true);
        });
    }
}