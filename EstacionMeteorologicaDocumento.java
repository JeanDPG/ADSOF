package estacion;

import documentos.IDocumento;
import Sensores.Sensor;
import Procesadores.Procesador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EstacionMeteorologicaDocumento implements IDocumento {

    private EstacionMeteorologica estacion;

    public EstacionMeteorologicaDocumento(EstacionMeteorologica estacion) {
        this.estacion = estacion;
    }

    @Override
    public String getTitulo() {
        return "Estación Meteorológica: " + estacion.getNombre();
    }

    @Override
    public String getSeccionPrincipal() {
        return estacion.getNombre();
    }

    @Override
    public List<String> getParrafos() {
        List<String> parrafos = new ArrayList<>();
        // Corrected order to longitude, latitude as per the example
        parrafos.add("Ubicacion: " + estacion.getLongitud() + ", " + estacion.getLatitud());
        parrafos.add("Sensores instalados: " + estacion.getSensoresRegistrados().size());
        parrafos.add("Última lectura: " + estacion.getUltimaLectura());
        return parrafos;
    }

    @Override
    public Map<String, List<String>> getColecciones() {
        Map<String, List<String>> colecciones = new HashMap<>();

        List<String> sensoresFormateados = new ArrayList<>();
        for (Sensor sensor : estacion.getSensoresRegistrados()) {
            Procesador procesador = sensor.getProcesador();
            String sensorStr = String.format("%s (%s)%s",
                    sensor.getId(),
                    sensor.getUnidadDeLectura().getSimbolo(),
                    procesador != null ? procesador.toString() : ""
            );
            sensoresFormateados.add(sensorStr);
        }
        colecciones.put("Sensores activos", sensoresFormateados);

        // Alertas activas (assuming no alerts for now as it's not in EstacionMeteorologica)
        // colecciones.put("Alertas activas", Collections.emptyList());

        return colecciones;
    }
}
