package Sensores.Alertas;
import java.time.LocalDateTime;

public class Alerta {
    private LocalDateTime fechaHora;
    private TipoDeAlerta tipo;
    private String mensaje;
    private String idSensor;

    public Alerta(String idSensor, TipoDeAlerta tipo,String mensaje) {
        this.fechaHora = LocalDateTime.now();
        this.tipo = tipo;
        this.idSensor = idSensor;
        this.mensaje = mensaje;
    }

    public String getIdSensor() { return idSensor; }

    @Override
    public String toString() {
        return String.format("- [%s] %s en %s: %s", 
                fechaHora, tipo.getNombre(), idSensor, mensaje);
    }
}
