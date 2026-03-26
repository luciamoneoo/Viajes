package com.proyect;

import java.nio.file.Path;

public interface Exportable {
    
    void toJSON(Path ruta);

    void fromJSON(Path ruta);
}
