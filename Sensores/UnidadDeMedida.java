package Sensores;

public enum UnidadDeMedida {
    CELSIUS("ºC"),
    FAHRENHEIT("ºF"),
    KELVIN("K"),
    PORCENTAJE("%"),
    HECTOPASCAL("hPa"),
    PASCAL("Pa"),
    MILIBAR("mbar");

    private final String simbolo;

    UnidadDeMedida(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
