package redSocial;

/**
 * Define los niveles de visibilidad o impacto de un elemento en la red social.
 */
public enum Exposicion {
    OCULTA,
    BAJA,
    MEDIA,
    ALTA,
    VIRAL;

    /**
     * Incrementa el nivel de exposición al siguiente escalón.
     * Si ya está en el nivel máximo (VIRAL), se mantiene igual.
     */
    public Exposicion siguiente() {
        int nextOrdinal = this.ordinal() + 1;
        Exposicion[] niveles = values();
        return (nextOrdinal < niveles.length) ? niveles[nextOrdinal] : this;
    }

    /**
     * Decrementa el nivel de exposición al escalón inferior.
     * Si ya está en el nivel mínimo (OCULTA), se mantiene igual.
     */
    public Exposicion anterior() {
        int prevOrdinal = this.ordinal() - 1;
        return (prevOrdinal >= 0) ? values()[prevOrdinal] : this;
    }

    /**
     * Determina si el nivel de exposición representa un impacto crítico.
     * @return true si el nivel es ALTA o VIRAL.
     */
    public boolean expCritica(){
        return this == ALTA || this == VIRAL;
    }
}
