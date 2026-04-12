package Procesadores.Conversores;

public class ConversorIdentidad implements Conversor {
    private String unidad;

    /** Crea un conversor que mantiene el mismo valor y la misma unidad. */
    public ConversorIdentidad(String unidad) { this.unidad = unidad; }

    @Override
    /** Devuelve el valor sin modificarlo. */
    public double convertir(double valor) { return valor; }
    @Override
    /** Devuelve la unidad de entrada del conversor identidad. */
    public String getUnidadOrigen() { return unidad; }
    @Override
    /** Devuelve la unidad de salida del conversor identidad. */
    public String getUnidadDestino() { return unidad; }
}
