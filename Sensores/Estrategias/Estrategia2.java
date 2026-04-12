package Sensores.Estrategias;

import java.util.concurrent.ThreadLocalRandom;

public class Estrategia2 implements EstrategiaSimulacion {
    private double ultimoValor;
    private double variacion; // Variación en porcentaje (ej: 0.1 = 10%)

    /** Configura el valor inicial y el porcentaje de variación permitido. */
    public Estrategia2(double ultimoValor, double variacion) {
        this.ultimoValor = ultimoValor;
        this.variacion = variacion;
    }

    @Override
    /** Genera un valor cercano al último valor producido y actualiza el estado interno. */
    public double generarValorAleat() {
        double rango = Math.abs(ultimoValor * variacion);
        if (rango == 0f) {
            return ultimoValor;
        }

        double nuevoValor = ultimoValor + ThreadLocalRandom.current().nextDouble(-rango, rango);
        this.ultimoValor = nuevoValor;
        return nuevoValor;
    }
}
