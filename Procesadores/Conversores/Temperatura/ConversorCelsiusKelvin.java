package Procesadores.Conversores.Temperatura;

import Procesadores.Conversores.Conversor;

public class ConversorCelsiusKelvin implements Conversor {
        @Override
        /** Convierte un valor de grados Celsius a Kelvin. */
        public double convertir(double valor) {
            return valor + 273.15;
        }
        @Override
        /** Indica que la unidad de entrada es Celsius. */
        public String getUnidadOrigen() { return "Celsius"; }
        @Override
        /** Indica que la unidad de salida es Kelvin. */
        public String getUnidadDestino() { return "Kelvin"; }

}
