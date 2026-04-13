package Procesadores;

import Procesadores.Conversores.Conversor;
import Procesadores.Conversores.ConversorIdentidad;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Procesador {

    Map<Date, Double> historico;
    private Conversor conversor;
    private double min;
    private double max;
    private double average;

    /** Inicializa el procesador con histórico vacío y conversor identidad por defecto. */
    public Procesador(){
        this.historico = new HashMap<>();
        this.conversor = new ConversorIdentidad("identidad");
    }

    /** Devuelve el histórico de valores indexado por fecha. */
    public Map<Date, Double> getHistorico() {
        return historico;
    }

    /** Devuelve el conversor asociado al procesador. */
    public Conversor getConversor() {
        return conversor;
    }

    /** Devuelve el valor mínimo registrado. */
    public double getMin() {
        return min;
    }

    /** Devuelve el valor máximo registrado. */
    public double getMax() {
        return max;
    }

    /** Devuelve la media de los valores registrados. */
    public double getAverage() {
        return average;
    }

    /** Sustituye el conversor actual por otro. */
    public void setConversor(Conversor conversor) {
        this.conversor = conversor;
    }

    /** Guarda un valor en el histórico y recalcula las métricas agregadas. */
    public void setValorHistorico(Date date, Double valor){
        double valorConvertido = this.conversor != null ? this.conversor.convertir(valor) : valor;
        this.historico.put(date, valorConvertido);
        this.min = Collections.min(this.historico.values());
        this.max = Collections.max(this.historico.values());
        this.average = average(this.historico);
    }

    /** Calcula la media aritmética de los valores del histórico recibido. */
    private double average(Map<Date, Double> historico) {
        double average = 0;
        for(Map.Entry<Date, Double> entry: historico.entrySet()){
            average += entry.getValue();
        }
        return average/historico.size();
    }


    @Override
    /** Devuelve una representación textual con conversor, valores y resumen estadístico. */
    public String toString() {
        String descripcionConversor = this.conversor != null
                ? " con conversor a " + this.conversor.getUnidadDestino()
                : "";
        StringBuilder valoresHistorico = new StringBuilder();

        for (Map.Entry<Date, Double> entry : this.historico.entrySet()) {
            if (!valoresHistorico.isEmpty()) {
                valoresHistorico.append(", ");
            }
            valoresHistorico.append(String.format("%.3f", entry.getValue()));
        }

        return descripcionConversor
                + " [" + valoresHistorico + "] "
                + " MIN: " + String.format("%.3f", this.getMin())
                + " MAX: " + String.format("%.3f", this.getMax())
                + " AVG: " + String.format("%.3f", this.getAverage());
    }
}
