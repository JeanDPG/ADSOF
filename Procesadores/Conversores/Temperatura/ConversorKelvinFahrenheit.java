package Procesadores.Conversores.Temperatura;

import Procesadores.Conversores.Conversor;

public class ConversorKelvinFahrenheit implements Conversor {
    @Override
    /** Convierte un valor de Kelvin a grados Fahrenheit. */
    public double convertir(double valor) {
        return ((valor - 273.15) * 1.8) + 32;
    }
    @Override
    /** Indica que la unidad de entrada es Kelvin. */
    public String getUnidadOrigen() { return "Kelvin"; }
    @Override
    /** Indica que la unidad de salida es Fahrenheit. */
    public String getUnidadDestino() { return "Fahrenheit"; }

}
