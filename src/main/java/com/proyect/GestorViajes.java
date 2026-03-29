package com.proyect;

import java.util.ArrayList;
import java.util.List;

public class GestorViajes {
    private List<Viajes> listaViajes;

    public void añadirViaje(Viajes viaje) {
        listaViajes.add(viaje);
    }

    public boolean eliminarViaje(String id) {
        return listaViajes.removeIf(viaje -> viaje.getId().equalsIgnoreCase(id));
    }

    public List<Viajes> getListaViajes() {
        return new ArrayList<>(listaViajes);
    }
}
