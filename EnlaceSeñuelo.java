package redSocial;

/**
 * Representa un enlace especial que incluye penalizaciones por coste extra 
 * y una probabilidad de retorno del mensaje.
 */
public class EnlaceSeñuelo extends Enlace {

    private int factCostExtra; // Factor multiplicador para el coste adicional
    private int probRetorno;   // Probabilidad de que el mensaje vuelva al origen

    /**
     * Constructor con coste base personalizado.
     */
    public EnlaceSeñuelo(Usuario uOrigen, Usuario uDestino, int coste, int probRetorno) {
        super(uOrigen, uDestino, coste);
        this.probRetorno = probRetorno;
    }

    /**
     * Constructor con coste base por defecto.
     */
    public EnlaceSeñuelo(Usuario uOrigen, Usuario uDestino, int probRetorno) {
        super(uOrigen, uDestino);
        this.probRetorno = probRetorno;
    }

    // --- Getters ---
    public int getFactCostExtra() { return factCostExtra; }
    public int getProbRetorno() { return probRetorno; }

    /**
     * Calcula el recargo adicional multiplicando el coste base por el factor extra.
     */
    @Override
    public int costeEspecial() {
        return this.getCoste() * this.factCostExtra;
    }

    /**
     * Calcula el coste total sumando el coste base y el coste especial.
     */
    @Override
    public int costeReal() {
        return this.getCoste() + costeEspecial();
    }
}
