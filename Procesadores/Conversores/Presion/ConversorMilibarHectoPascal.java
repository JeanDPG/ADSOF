package Procesadores.Conversores.Presion;

import Procesadores.Conversores.Conversor;

public class ConversorMilibarHectoPascal implements Conversor {
    @Override
    /** Convierte un valor de milibar a hectopascal. */
    public double convertir(double valor) {
        return valor;
    }

    @Override
    /** Indica que la unidad de entrada es milibar. */
    public String getUnidadOrigen() {
        return "mbar";
    }

    @Override
    /** Indica que la unidad de salida es hectopascal. */
    public String getUnidadDestino() {
        return "hPa";
    }
}
