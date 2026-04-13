package Sensores;

public enum UnidadDeMedida {
    CELSIUS("ºC", -273.15, 1000.0),
    FAHRENHEIT("ºF", -459.67, 1832.0), 
    KELVIN("K", 0.0, 1273.15),        
    PORCENTAJE("%", 0.0, 100.0),
    HECTOPASCAL("hPa", 300.0, 1100.0),
    PASCAL("Pa", 30000.0, 110000.0),
    MILIBAR("mbar", 300.0, 1100.0);

    private final String simbolo;
    private final double min;
    private final double max;

    UnidadDeMedida(String simbolo, double min, double max) {
        this.simbolo = simbolo;
        this.min = min;
        this.max = max;
    }

    public String getSimbolo() { return simbolo; }
    public double getMin() { return min; }
    public double getMax() { return max; }
}
