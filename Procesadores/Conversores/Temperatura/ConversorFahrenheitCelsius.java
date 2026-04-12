package Procesadores.Conversores.Temperatura;

import Procesadores.Conversores.Conversor;

public class ConversorFahrenheitCelsius implements Conversor {
    @Override
    /** Convierte un valor de grados Fahrenheit a Celsius. */
    public double convertir(double valor) {
        return (valor - 32) / 1.8;
    }
    @Override
    /** Indica que la unidad de entrada es Fahrenheit. */
    public String getUnidadOrigen() { return "Fahrenheit"; }
    @Override
    /** Indica que la unidad de salida es Celsius. */
    public String getUnidadDestino() { return "Celsius"; }

}
