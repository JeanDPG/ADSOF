package Procesadores.Conversores;

public class UnidadIncompatibleException extends Exception {
    /** Crea una excepción para señalar que dos conversores no pueden encadenarse. */
    public UnidadIncompatibleException(Conversor primero, Conversor segundo) {
        super("No se puede concatenar: " + primero.getUnidadDestino() + " no coincide con " + segundo.getUnidadOrigen());
    }
}
