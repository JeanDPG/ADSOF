package Procesadores.Conversores.Presion;

import Procesadores.Conversores.Conversor;

public class ConversorHectoPascalPascal implements Conversor {
    @Override
    /** Convierte un valor de hectopascales a pascales. */
    public double convertir(double valor) {
        return valor * 100;
    }

    @Override
    /** Indica que la unidad de entrada es hectopascal. */
    public String getUnidadOrigen() {
        return "hPa";
    }

    @Override
    /** Indica que la unidad de salida es pascal. */
    public String getUnidadDestino() {
        return "Pa";
    }
}
