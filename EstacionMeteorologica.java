package estacion;

import Sensores.Sensor;
import Sensores.SensorDuplicadoException;
import Sensores.TipoSensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstacionMeteorologica {
    private String nombre;
    private double latitud;
    private double longitud;
    private List<Sensor> sensores;

    public EstacionMeteorologica(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.sensores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    // Placeholder for now, as per the example
    public String getUltimaLectura() {
        return "2026-03-12T20:42:29";
    }

    /**
     * Añade un sensor. Antes de añadir, recorre la lista para
     * comprobar que el ID no esté repetido.
     */
    public void añadirSensor(Sensor nuevoSensor) throws SensorDuplicadoException {
        for (Sensor s : sensores) {
            if (s.getId().equals(nuevoSensor.getId())) {
                throw new SensorDuplicadoException(s, nuevoSensor);
            }
        }
        sensores.add(nuevoSensor);
    }

    public List<Sensor> getSensoresRegistrados() {
        return Collections.unmodifiableList(this.sensores);
    }

    /**
     * Busca un sensor recorriendo la lista por ID.
     */
    public Sensor recuperarSensorPorId(String id) {
        for (Sensor s : sensores) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Filtra los sensores por tipo (TEMP, HUM o PRES).
     */
    public List<Sensor> obtenerSensoresPorTipo(TipoSensor tipoBuscado) {
        List<Sensor> filtrados = new ArrayList<>();
        for (Sensor s : sensores) {
            if (s.getTipo() == tipoBuscado) { // Comparación directa de Enum (muy rápida y segura)
                filtrados.add(s);
            }
        }
        return filtrados;
    }

    public void mostrarSensores() {
        System.out.println(sensores.toString());
    }

    @Override
    public String toString() {
        return "Estación: " + nombre + " (Lat: " + latitud + ", Lon: " + longitud + ")";
    }
}
