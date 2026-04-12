import Sensores.*;

import java.time.LocalDateTime;

public class SensorSinCalibrarException extends AlertaException {

    private final Sensor sensor;
    private final LocalDateTime fechaCaducidad;
    private final LocalDateTime timestamp;

    public SensorSinCalibrarException(Sensor sensor, LocalDateTime fechaCaducidad) {
        // Formato: Sensor TEMP-0003 sin calibrar (calibración caducada desde 2026-01-01T00:00:00)
        super(String.format("Sensor %s sin calibrar (calibración caducada desde %s)",
                sensor.getId(),
                fechaCaducidad != null ? fechaCaducidad : "fecha desconocida"));
        this.sensor = sensor;
        this.fechaCaducidad = fechaCaducidad;
        this.timestamp = LocalDateTime.now();
    }

    public Sensor getSensor() { return sensor; }
    public LocalDateTime getFechaCaducidad() { return fechaCaducidad; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
