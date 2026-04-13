//gestiona la lista en memoria paraañadir, eliminar, buscar y ordenar

package com.proyect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//gestiona la colección de viajes en memoria y proporciona métodos para añadir, eliminar, buscar y ordenar viajes
public class GestorViajes {

    // lista donde guardaremosn todos los viajes.
    private List<Viajes> listaViajes = new ArrayList<>();

    // aqui el Map agrupa los viajes dependiedno del destino asi se buscarán mas rápidp
    // Clave = nombre del destino, Valor = lista de viajes a ese destino
    private Map<String, List<Viajes>> indicePorDestino = new HashMap<>();

    // añade un viaje al arraylist y también al índice por destino
    public void añadirViaje(Viajes viaje) {
        listaViajes.add(viaje);
        indicePorDestino
            .computeIfAbsent(viaje.getDestino(), k -> new ArrayList<>()) //sirve para que si el destino no existe en el mapa, cree una lista nueva para él
            .add(viaje);
    }

    // elimina un viaje buscándolo por su ID, mas facil de identificar
    //si el programa encuentra el viaje, devuelve true, si no existe devolverá false
    public boolean eliminarViaje(String id) {
        boolean eliminado = false;

        // usamos Iterator para poder borrar elementos de la lista mientras la recorremos
        Iterator<Viajes> it = listaViajes.iterator();
        while (it.hasNext()) {
            Viajes v = it.next();
            if (v.getId().equalsIgnoreCase(id)) {
                it.remove();// borramos del arraylist
                // tambien borraremos del índice por destino para mantenerlo actualizado

                List<Viajes> grupo = indicePorDestino.get(v.getDestino());
                if (grupo != null) grupo.remove(v);
                eliminado = true;
            }
        }
        return eliminado;
    }

    // aqui nos devuelve la lista de viajesy asi nadie la podrá modificar directamente, si queremos modificarla, lo haremos a través de los métodos del gestor
    public List<Viajes> getListaViajes() {
        return new ArrayList<>(listaViajes);
    }

    // aqui nos devuelve el índice por destino, para que nadie lo modifique desde fuera tambien
    public Map<String, List<Viajes>> getIndicePorDestino() {
        return indicePorDestino;
    }

    // busca viajes por texto en el nombre o destino, devuelve una lista de viajes que coincidan con el texto buscado
    public List<Viajes> buscarPorTexto(String texto) {
        String t = texto.toLowerCase();
        return listaViajes.stream()
            .filter(v -> v.getNombre().toLowerCase().contains(t)
                      || v.getDestino().toLowerCase().contains(t))
            .collect(Collectors.toList());
    }

    // ordena los viajes por precio de menor a mayor y devuelve una nueva lista ordenada
    public List<Viajes> ordenadosPorPrecio() {
        return listaViajes.stream()
            .sorted((a, b) -> Double.compare(a.calcularPrecio(), b.calcularPrecio()))
            .collect(Collectors.toList());
    }

    // agrupa los viajes por tipo (nacional o internacional) y devuelve un mapa donde la clave es el tipo y el valor es la lista de viajes de ese tipo
    public Map<String, List<Viajes>> agrupadosPorTipo() {
        return listaViajes.stream()
            .collect(Collectors.groupingBy(v ->
                v instanceof ViajesInternacionales ? "Internacional" : "Nacional"));
    }

    // calcula el precio total de todos los viajes sumando el precio de cada uno y devuelve el resultado
    public double precioTotal() {
        return listaViajes.stream()
            .mapToDouble(Viajes::calcularPrecio)
            .sum();
    }
}
