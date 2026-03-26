package com.proyect;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    static ArrayList<Viajes> viajes = new ArrayList<>(); // creamos un arraylist para que al imprimir los viajes se impriman en lista

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opciones;

        do { // hacemso que recorra el bucle hasta bque el uuario elija la tercera opcion

            System.out.println(" MENU ");
            System.out.println("1. Añadir viajes");
            System.out.println("2. Mostrar mis viajes");
            System.out.println("3. Salir");

            opciones = sc.nextInt();

            switch(opciones){

                case 1:
                    añadirViajes(sc);
                    break;

                case 2:
                    mostrarViajes();
                    break;
            }

        } while(opciones != 3); // si elijes salir del programa, salimos del bucle
    }

    public static void añadirViajes(Scanner sc){ // aqui escribiremos nosotros los viajes que queramos añadir
    }

    public static void mostrarViajes(){ // despuies le pedimos al programa que nos imprimo todos los viajes que tenemos guardados
    }

}
  
