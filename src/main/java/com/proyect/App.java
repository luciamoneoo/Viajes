// es el pnto de entrada al ejecutar, uno arranca la consola que seria app ,y otro la ventana que sería appGUI

package com.proyect;



public class App {
    // en el main, creamos el gestor y el guardado, cargamos los viajes del fichero al gestor y arrancamos la consola
    public static void main(String[] args) {
        GestorViajes gestor = new GestorViajes();
        ViajesGuardados guardado = new ViajesGuardados();

        for (Viajes v : guardado.fromCSV()) {
            // añadimos al gestor cada viaje que nos devuelve el método fromCSV del guardado
            //  que lee el fichero y lo convierte en objetos Viajes
            gestor.añadirViaje(v);
        }
        GestionViajes consola = new GestionViajes(gestor, guardado);

        consola.iniciar(); // arrancamos el bucle del menú de la consola

        
}
}
