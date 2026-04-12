package Sensores.Estrategias;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Estrategia3 implements EstrategiaSimulacion {
    private List<Double> valores;
    private double variacion; // Variación en porcentaje

    /** Configura la muestra histórica y la variación aplicada sobre su media. */
    public Estrategia3(List<Double> valores, double variacion) {
        this.valores = valores;
        this.variacion = variacion;
    }

    @Override
    /** Genera un valor aleatorio alrededor de la media de la lista recibida. */
    public double generarValorAleat() {
        double media = 0;
        for (Double f : valores) {
            media += f;
        }
        media /= valores.size();

        double rango = Math.abs(media * variacion);
        if (rango == 0f) {
            return media;
        }

        return media + ThreadLocalRandom.current().nextDouble(-rango, rango);
    }
}
