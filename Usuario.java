package redsocial;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Usuario {

    private String nombre;
    private int capacidadAmp;
    private Map<Date, Enlace> enlacesSalientes;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.capacidadAmp = 2;
        this.enlacesSalientes = new TreeMap<>();
    }

    public Usuario(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadAmp = capacidad;
        this.enlacesSalientes = new TreeMap<>();
    }

    public boolean addEnlace(Enlace e) {
        if (e.getUsuarioOrigen().equalsIgnoreCase(this.nombre) &&
                !(e.getUsuarioDestino().equalsIgnoreCase(this.nombre))) {
            for (Map.Entry<Date, Enlace> entrada : enlacesSalientes.entrySet()) {
                if (entrada.getValue().getUsuarioDestino().equalsIgnoreCase(e.getUsuarioDestino())) {
                    return false;
                }
            }
            this.enlacesSalientes.put(new Date(), e);
            return true;
        }
        return false;
    }

    public boolean addEnlace(String usuarioDestino, int coste) {
        if (usuarioDestino.equalsIgnoreCase(this.nombre)){
            for (Map.Entry<Date, Enlace> entrada : enlacesSalientes.entrySet()) {
                if (entrada.getValue().getUsuarioDestino().equalsIgnoreCase(usuarioDestino)) {
                    return false;
                }
            }
            this.enlacesSalientes.put(new Date(),new Enlace(this.nombre,usuarioDestino,coste));
            return true;
        }

        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadAmp() {
        return capacidadAmp;
    }

    public Map<Date, Enlace> getEnlacesSalientes() {
        return enlacesSalientes;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadAmp(int capacidadAmp) {
        this.capacidadAmp = capacidadAmp;
    }

    public void setEnlacesSalientes(Map<Date, Enlace> enlacesSalientes) {
        this.enlacesSalientes = enlacesSalientes;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", capacidadAmp=" + capacidadAmp +
                ", enlacesSalientes=" + enlacesSalientes +
                '}';
    }
}


}
