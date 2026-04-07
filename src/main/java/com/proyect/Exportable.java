package com.proyect;

import java.nio.file.Path;

public interface Exportable {
    
    void toCSV(Path ruta);

    void fromCSV(Path ruta);
}
