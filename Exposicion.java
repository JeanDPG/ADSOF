package redSocial;

public enum Exposicion {
    OCULTA,
    BAJA,
    MEDIA,
    ALTA,
    VIRAL;

    public Exposicion siguiente() {
        int nextOrdinal = this.ordinal() + 1;
        Exposicion[] niveles = values();
        return (nextOrdinal < niveles.length) ? niveles[nextOrdinal] : this;
    }

    public Exposicion anterior() {
        int prevOrdinal = this.ordinal() - 1;
        return (prevOrdinal >= 0) ? values()[prevOrdinal] : this;
    }

    public boolean expCritica(){
        return this == ALTA || this == VIRAL;
    }

}