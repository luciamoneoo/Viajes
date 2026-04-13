// interfaz que obliga a tener los métodos toCSV() y fromCSV()

package com.proyect;

import java.nio.file.Path;
import java.util.List;

// la interfaz define un "contrato" que obliga a quien la implemente a tener estos dos métodos
// como ViajesGuardados implementa esta interfaz, debe tener toCSV() y fromCSV()
public interface Exportable {
    
    // guarda la lista de viajes en un fichero CSV
    void toCSV(List<Viajes> lista);

    // Lee el fichero CSV y devuelve una lista de viajes cargados desde disco
    List<Viajes> fromCSV();

}
