package trayectos;

public enum Linea {
    C1("azul claro", 5), 
    C4("azul oscuro", 10), 
    C5("amarilla", 30);

    private String color;
    private int tiempoEntreParadas; 

    private Linea(String color, int tiempoEntreParadas) {
        this.color = color;
        this.tiempoEntreParadas = tiempoEntreParadas;
    }

    public int getTiempoEntreParadas() {
        return tiempoEntreParadas;
    }

    @Override
    public String toString() {
        return name() + " (" + this.color + ")";
    }
}
