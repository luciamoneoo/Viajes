package com.proyect;

import java.nio.file.Path;
import java.util.List;

public interface Exportable {
    
    void toCSV(List<Viajes> lista);

    List<Viajes> fromCSV();

}
