package com.proyect;

public class ViajesInternacionales extends Viajes {

    public ViajesInternacionales(String nombre, int nPersonas, String destino, double precio, int dias, String transporte, String alojamiento) {
        super(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
    }

    @Override
    public double calcularPrecio() {
        return precio + 200; // tasas internacionales
    }

    @Override
    public String resumenViaje() {
        return "Viaje internacional a " + destino + " durante " + dias + " días.";
    }
}