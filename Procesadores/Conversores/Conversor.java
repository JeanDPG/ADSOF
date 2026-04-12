package Procesadores.Conversores;


public interface Conversor {
    /** Convierte un valor desde la unidad de origen a la unidad de destino. */
    double convertir(double valor);
    /** Devuelve la unidad que espera este conversor como entrada. */
    String getUnidadOrigen();
    /** Devuelve la unidad producida por este conversor como salida. */
    String getUnidadDestino();

    /** Encadena este conversor con otro compatible para aplicar ambas conversiones seguidas. */
    default Conversor concatenarCon(Conversor siguiente) throws UnidadIncompatibleException {
        if (!this.getUnidadDestino().equalsIgnoreCase(siguiente.getUnidadOrigen())) {
            throw new UnidadIncompatibleException("No se puede concatenar: " +
                    this.getUnidadDestino() + " no coincide con " + siguiente.getUnidadOrigen());
        }
        return new ConversorCompuesto(this, siguiente);
    }
}
