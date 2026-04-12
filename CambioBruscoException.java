
import java.time.LocalDateTime;
public class CambioBruscoException extends AlertaException {

    private final Sensor sensor;
    private final double lecturaAnterior;
    private final double lecturaActual;
    private final double porcentajeCambio;
    private final LocalDateTime timestamp;

    public CambioBruscoException(Sensor sensor, double lecturaAnterior,
                                 double lecturaActual, double porcentajeCambio) {
        super(String.format("Cambio brusco en %s: %.1f%s (anterior: %.1f%s)",
                sensor.getId(),
                lecturaActual, sensor.getUnidadDeLectura(),
                lecturaAnterior, sensor.getUnidadDeLectura()));
        this.sensor          = sensor;
        this.lecturaAnterior = lecturaAnterior;
        this.lecturaActual   = lecturaActual;
        this.porcentajeCambio = porcentajeCambio;
        this.timestamp       = LocalDateTime.now();
    }

    public Sensor getSensor()            { return sensor; }
    public double getLecturaAnterior()   { return lecturaAnterior; }
    public double getLecturaActual()     { return lecturaActual; }
    public double getPorcentajeCambio()  { return porcentajeCambio; }
    public LocalDateTime getTimestamp()  { return timestamp; }
}
