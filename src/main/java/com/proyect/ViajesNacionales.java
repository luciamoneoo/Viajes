//hereda de Viajes y la única diferencia con los viajes internacionales es que la internacional suma 200€ al precio
//subclase de Viajes que representa un viaje dentro del país y añade su propia lógica en el preci y su resumen

package com.proyect;

public class ViajesNacionales extends Viajes {

    // constructor que llama al padre con todos los datos
    public ViajesNacionales(String nombre, int nPersonas, String destino, double precio, int dias, String transporte, String alojamiento) {
        super(nombre, nPersonas, destino, precio, dias, transporte, alojamiento);
    }

    // como en viajes nacionales el precio no tiene ningún extra, se devuelve tal cual
    @Override
        public double calcularPrecio() {
            return precio;
    }
    // nos devuelve una frase corta describiendo el viaje nacional
    @Override
        public String resumenViaje() {
            return "Viaje a " + destino + " durante " + dias + " días.";
    }

}   
