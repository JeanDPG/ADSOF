package Procesadores.Conversores.Presion;

import Procesadores.Conversores.Conversor;

public class ConversorPascalMilibar implements Conversor {
    @Override
    /** Convierte un valor de pascales a milibares. */
    public double convertir(double valor) {
        return valor / 100;
    }

    @Override
    /** Indica que la unidad de entrada es pascal. */
    public String getUnidadOrigen() {
        return "Pa";
    }

    @Override
    /** Indica que la unidad de salida es milibar. */
    public String getUnidadDestino() {
        return "mbar";
    }
}
