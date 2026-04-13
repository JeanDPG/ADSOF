package Procesadores.Conversores;

 class ConversorCompuesto implements Conversor {
    private Conversor primero;
    private Conversor segundo;

    /** Crea un conversor que aplica dos conversiones consecutivas. */
    ConversorCompuesto(Conversor primero, Conversor segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    @Override
    /** Convierte el valor aplicando primero el primer conversor y después el segundo. */
    public double convertir(double valor) {
        return segundo.convertir(primero.convertir(valor));
    }

    @Override
    /** Devuelve la unidad de origen del primer conversor. */
    public String getUnidadOrigen() { return primero.getUnidadOrigen(); }

    @Override
    /** Devuelve la unidad de destino del segundo conversor. */
    public String getUnidadDestino() { return segundo.getUnidadDestino(); }
}
