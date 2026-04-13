
import Sensores.*;
import Sensores.Alertas.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Excepciones.CambioBruscoException;
import Excepciones.LecturaFueraDeRangoException;
import Excepciones.SensorSinCalibrarException;

public class EstacionMeteorologica {
    private String nombre;
    private double latitud;
    private double longitud;
    private List<Sensor> sensores;
	private List<Alerta> historicoAlertas;
    private LocalDateTime fechaUltimaLecturaGlobal;
    private int lecturasRealizadas = 0;

    public EstacionMeteorologica(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.sensores = new ArrayList<>();
		this.historicoAlertas = new ArrayList<>();
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

/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Devuelve una lista inmutable de sensores registrados.
 * @return una lista inmutable de sensores registrados.
 */
    /*******  5146aec5-003e-4aee-b6b0-b9013d34478d  *******/
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

    public void realizarLecturaSimultanea() {
        for (Sensor s : sensores) {
            try {
                s.realizarLectura();
            } catch (CambioBruscoException e) {
                historicoAlertas.add(new Alerta(s.getId(),TipoDeAlerta.CAMBIO_BRUSCO, e.getMessage()));
            } catch (LecturaFueraDeRangoException e) {
                historicoAlertas.add(new Alerta(s.getId(),TipoDeAlerta.FUERA_DE_RANGO ,e.getMessage()));
                s.setMedicionDetenida(true);
            } catch (SensorSinCalibrarException e) {
                historicoAlertas.add(new Alerta(s.getId(),TipoDeAlerta.SIN_CALIBRAR , e.getMessage()));
            }
        }
    }

    public void lecturaPeriodica(int periodoSegundos, int numLecturas) {
            if (numLecturas-lecturasRealizadas <= 0) {
            return; // Ya hemos llegado al máximo
        }

        LocalDateTime ahora = LocalDateTime.now();

        
        if (fechaUltimaLecturaGlobal == null || 
            java.time.Duration.between(fechaUltimaLecturaGlobal, ahora).toSeconds() >= periodoSegundos) {
            
            realizarLecturaSimultanea();
            this.fechaUltimaLecturaGlobal = ahora;
            this.lecturasRealizadas++;
            
            System.out.println("Lectura automática realizada (" + lecturasRealizadas + "/" + numLecturas + ")");
        }
    }
    public void configurarLecturaPeriodicaBucle(int periodoSegundos, int maxLecturas) {
    for (int i = 1; i <= maxLecturas; i++) {
        realizarLecturaSimultanea();
        System.out.println("Lectura en bucle realizada (" + i + "/" + maxLecturas + ")");

        if (i < maxLecturas) {
            try {
                // El programa se "congela" aquí los segundos indicados
                Thread.sleep(periodoSegundos * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
    /**
     * Muestra la lista de sensores registrados siguiendo el formato del enunciado.
     */
    public void mostrarSensores() {
        System.out.println(sensores.toString());
    }

	public void calibrarSensor(String id, double nuevoOffset) {
	    Sensor s = recuperarSensorPorId(id);
	    if (s != null) {
	        s.calibrar(nuevoOffset);
	        s.setMedicionDetenida(false);
	        for (int i = historicoAlertas.size() - 1; i >= 0; i--) {
	            Alerta alertaActual = historicoAlertas.get(i);
	            if (alertaActual.getIdSensor().equals(id)) {
	                historicoAlertas.remove(i);
	            }
	        }
	    }
	}

    public void mostrarAlertas() {
        System.out.println("Alertas activas: " + historicoAlertas.size());
        for (Alerta a : historicoAlertas) {
            System.out.println("- " + a.toString());
        }
    }
	/*
    public void configurarLecturaPeriodica(int periodoSegundos, int maxLecturas) {

    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estación Meteorológica: ").append(nombre).append("\n");
        sb.append("Ubicación: ").append(latitud).append(", ").append(longitud).append("\n");
        sb.append("--------------------------------------\n");
        sb.append("Sensores instalados: ").append(sensores.size()).append("\n");
        
        // Listado de sensores
        for (Sensor s : sensores) {
            sb.append(s.toString()).append("\n");
        }
        
        sb.append("\nAlertas activas: ").append(historicoAlertas.size()).append("\n");
        for (Alerta a : historicoAlertas) {
            sb.append("- ").append(a.toString()).append("\n");
        }
        
        return sb.toString();
    }
}
