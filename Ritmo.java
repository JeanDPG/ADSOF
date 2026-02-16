package trayectos;

public enum Ritmo {
    SUAVE(15), MODERADO(10), RAPIDO(8);

    private int minutosPorKm;

    private Ritmo(int minutosPorKm) {
        this.minutosPorKm = minutosPorKm;
    }

    public int getMinutosPorKm() {
        return minutosPorKm;
    }
}
