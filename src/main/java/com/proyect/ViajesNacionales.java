package com.proyect;

public class ViajesNacionales extends Viajes {

    public ViajesNacionales(String nombre, int nPersonas, String destino, double precio, int dias, String transporte, String alojamiento) {
        super(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
    }

    @Override
    public double calcularPrecio() {
        return precio;
    }

    @Override
    public String resumenViaje() {
        return "Viaje a " + destino + " durante " + dias + " días.";
    }
}
