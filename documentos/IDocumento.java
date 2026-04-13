package documentos;

import java.util.List;
import java.util.Map;

public interface IDocumento {

    String getTitulo();
    String getSeccionPrincipal();
    List<String> getParrafos();
    // Mapa donde la clave es el título de la lista (ej: "Sensores activos")
    // y el valor es la lista de elementos.
    Map<String, List<String>> getColecciones();

}
