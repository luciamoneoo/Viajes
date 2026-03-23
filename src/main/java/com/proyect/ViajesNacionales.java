package com.proyect;

public class ViajesNacionales extends Viajes {

    public ViajesNacionales(String destino, double precio, int dias, String transporte, String alojamiento) {
        super(destino, precio, dias, transporte, alojamiento);
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
