// plantilla base (abstracta) que efine los atributos comunes y genera el ID automático

// no podremos crear un objeto aqui directamente, solo a través de sus subclases

package com.proyect;
public abstract class Viajes {

    // la variable estática es compartida por todos los objetos Viajes
    //aquí generamos un ID único y automático a cada viaje (001, 002, 003...)
    private static int contadorId = 1;


    // atributos de la clase Viajes, que serán heredados por las subclases ViajesNacionales e Internacionales
    // protected significa que las subclases también pueden usarlos directamente
    protected String nombre;
    protected String Id;
    protected int nPersonas;
    protected String destino;
    protected double precio;
    protected int dias;
    protected String transporte;
    protected String alojamiento;

    // el constructorse ejecuta cada vez que se crea un viaje nuevo
    // Asigna todos los datos y genera automáticamente el ID con formato V001, V002...
    public Viajes(String nombre, int nPersonas, String destino, double precio, int dias, String transporte, String alojamiento){
        this.nombre = nombre;
        this.nPersonas = nPersonas;
        this.destino = destino;
        this.precio = precio;
        this.dias = dias;
        this.transporte = transporte;
        this.alojamiento = alojamiento;

        // hacrmos el diseño para que cada vez que se creen los id, salgan 2 ceros a la izquierda
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

    // los métodos abstractos en cada subclase DEBE implementarlos a su maner
    // calcularPrecio() en Nacional devuelve el precio tal cual yen Internacional suma 200€ por un visado
    public abstract double calcularPrecio();

    // resumenViaje() devuelve una frase sobre el viaje
    public abstract String resumenViaje();
    
    // los getters sonmétodos para leer los atributos desde fuera de la clase
    public String getId() {
    return Id; 
    }

    public String getNombre() {
    return nombre; 
    }

    public String getDestino() {
    return destino; 
    }

    public String getTransporte() {
    return transporte; 
    }

    public String getAlojamiento() {
    return alojamiento; 
    }

    public double getPrecio() {
    return precio; 
    }

    public int getDias() {
    return dias; 
    }

    public int nPersonas() {
    return nPersonas;
    }

}