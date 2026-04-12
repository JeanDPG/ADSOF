package Sensores;

public enum TipoSensor {
    TEMPERATURA("TEMP"),
    HUMEDAD("HUM"),
    PRESION("PRES");

    private final String prefijo;

    TipoSensor(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getPrefijo() {
        return prefijo;
    }
}