package com.proyect;
public abstract class Viajes {

    protected String destino;
    protected double precio;
    protected int dias;
    protected String transporte;
    protected String alojamiento;

    public Viajes(String destino, double precio, int dias, String transporte, String alojamiento){
        this.destino = destino;
        this.precio = precio;
        this.dias = dias;
        this.transporte = transporte;
        this.alojamiento = alojamiento;
    }

    public abstract double calcularPrecio();

    public abstract String resumenViaje();
    
}