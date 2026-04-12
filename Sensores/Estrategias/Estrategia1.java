package Sensores.Estrategias;

import java.util.concurrent.ThreadLocalRandom;


public class Estrategia1 implements EstrategiaSimulacion {
    private double min, max, probError;

    /** Configura el rango válido y la probabilidad de generar un valor erróneo. */
    public Estrategia1(double min, double max, double probError) {
        this.min = min;
        this.max = max;
        this.probError = probError;
    }

    @Override
    /** Genera un valor aleatorio válido o fuera de rango según la probabilidad de error. */
    public double generarValorAleat() {
        int numeroAleatorio = ThreadLocalRandom.current().nextInt(1, 101);
        double resultado = 0;

        if (numeroAleatorio <= probError * 100) {
            resultado = ThreadLocalRandom.current().nextDouble() * 1000; // Fuera de rango
        } else {
            resultado =  ThreadLocalRandom.current().nextDouble(min, max);
        }
        return resultado;
    }
}
