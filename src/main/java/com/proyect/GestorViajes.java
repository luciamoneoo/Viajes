package com.proyect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestorViajes {

    private List<Viajes> listaViajes = new ArrayList<>();

    private Map<String, List<Viajes>> indicePorDestino = new HashMap<>();

    public void añadirViaje(Viajes viaje) {
        listaViajes.add(viaje);
        indicePorDestino
            .computeIfAbsent(viaje.getDestino(), k -> new ArrayList<>())
            .add(viaje);
    }

    public boolean eliminarViaje(String id) {
        boolean eliminado = false;
        Iterator<Viajes> it = listaViajes.iterator();
        while (it.hasNext()) {
            Viajes v = it.next();
            if (v.getId().equalsIgnoreCase(id)) {
                it.remove();
                // limpiamos también el índice
                List<Viajes> grupo = indicePorDestino.get(v.getDestino());
                if (grupo != null) grupo.remove(v);
                eliminado = true;
            }
        }
        return eliminado;
    }

    public List<Viajes> getListaViajes() {
        return new ArrayList<>(listaViajes);
    }

    public Map<String, List<Viajes>> getIndicePorDestino() {
        return indicePorDestino;
    }

    public List<Viajes> buscarPorTexto(String texto) {
        String t = texto.toLowerCase();
        return listaViajes.stream()
            .filter(v -> v.getNombre().toLowerCase().contains(t)
                      || v.getDestino().toLowerCase().contains(t))
            .collect(Collectors.toList());
    }

    public List<Viajes> ordenadosPorPrecio() {
        return listaViajes.stream()
            .sorted((a, b) -> Double.compare(a.calcularPrecio(), b.calcularPrecio()))
            .collect(Collectors.toList());
    }

    public Map<String, List<Viajes>> agrupadosPorTipo() {
        return listaViajes.stream()
            .collect(Collectors.groupingBy(v ->
                v instanceof ViajesInternacionales ? "Internacional" : "Nacional"));
    }

    public double precioTotal() {
        return listaViajes.stream()
            .mapToDouble(Viajes::calcularPrecio)
            .sum();
    }
}
