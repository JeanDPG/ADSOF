
package Sensores.Alertas;
public enum TipoDeAlerta {
    CAMBIO_BRUSCO("Cambio brusco"),
    SIN_CALIBRAR("Sensor sin calibrar"),
    FUERA_DE_RANGO("Lectura fuera de rango");

    private final String nombre;

    TipoDeAlerta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
