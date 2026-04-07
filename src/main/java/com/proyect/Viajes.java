package com.proyect;
public abstract class Viajes {

    private static int contadorId = 1;

    protected String nombre;
    protected String Id;
    protected int nPersonas;
    protected String destino;
    protected double precio;
    protected int dias;
    protected String transporte;
    protected String alojamiento;

    public Viajes(String nombre, int nPersonas, String destino, double precio, int dias, String transporte, String alojamiento){
        this.nombre = nombre;
        this.nPersonas = nPersonas;
        this.destino = destino;
        this.precio = precio;
        this.dias = dias;
        this.transporte = transporte;
        this.alojamiento = alojamiento;
        String numero = "";
    if (contadorId < 10) {
    numero = "00" + contadorId;
    } else if (contadorId < 100) {
    numero = "0" + contadorId;
    } else 
        {
    numero = "" + contadorId;
    }
    this.Id = "V" + numero;
        contadorId++;
    }

    public abstract double calcularPrecio();

    public abstract String resumenViaje();
    
    public String getId() {
    return Id; 
    }

    public String getNombre() {
    return nombre; 
    }

    public String getDestino() {
    return destino; 
    }

    public double getPrecio() {
    return precio; 
    }

    public int getDias() {
    return dias; 
    }

}