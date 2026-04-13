package Excepciones;
import Sensores.*;

import java.time.LocalDateTime;

public class LecturaFueraDeRangoException extends AlertaException {

    private final Sensor sensor;
    private final double valorLeido;
    private final LocalDateTime timestamp;

    public LecturaFueraDeRangoException(Sensor sensor, double valorLeido) {
        // Formato: Lectura fuera de rango en HUM-0002: 105.0%
        super(String.format("Lectura fuera de rango en %s: %.1f%s",
                sensor.getId(),
                valorLeido, sensor.getUnidadDeLectura()));
        this.sensor = sensor;
        this.valorLeido = valorLeido;
        this.timestamp = LocalDateTime.now();
    }

    public Sensor getSensor() { return sensor; }
    public double getValorLeido() { return valorLeido; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
