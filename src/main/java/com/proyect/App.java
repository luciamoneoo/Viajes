package com.proyect;



public class App {
    public static void main(String[] args) {
        // 1. Instanciamos la lógica (colecciones)
        GestorViajes gestor = new GestorViajes();
        
        // 2. Instanciamos la consola pasándole el gestor
        GestionViajes consola = new GestionViajes(gestor);
        
        // 3. Arrancamos el menú interactivo
        consola.iniciar();
    }
}
  
