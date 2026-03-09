package redSocial;

import java.util.*;

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
        if (e.getUsuarioOrigen().equals(this) &&
                !(e.getUsuarioDestino().equals(this))) {
            for (Map.Entry<Date, Enlace> entrada : enlacesSalientes.entrySet()) {
                if (entrada.getValue().getUsuarioDestino().equals(e.getUsuarioDestino())) {
                    return false;
                }
            }
            this.enlacesSalientes.put(new Date(), e);
            return true;
        }
        return false;
    }

    public boolean addEnlace(Usuario usuarioDestino, int coste) {
        if (!(usuarioDestino.equals(this))){
            for (Map.Entry<Date, Enlace> entrada : enlacesSalientes.entrySet()) {
                if (entrada.getValue().getUsuarioDestino().equals(usuarioDestino)) {
                    return false;
                }
            }
            this.enlacesSalientes.put(new Date(),new Enlace(this,usuarioDestino,coste));
            return true;
        }

        return false;
    }
    public Enlace getEnlace(Usuario usuarioDestino) {

        for (Enlace e : enlacesSalientes.values()) {
            if (e.getUsuarioDestino().equals(usuarioDestino)) {
                return e;
            }
        }

        return null;
    }
    public Enlace getEnlace(int i) {
        if (i < 0 || i >= enlacesSalientes.size()) {
            return null;
        }
        int contador = 0;
        for (Enlace e : enlacesSalientes.values()) {
            if (contador == i) {
                return e;
            }
            contador++;
        }
        return null;
    }
    public int getNumEnlaces() {
        return enlacesSalientes.size();
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

        StringBuilder sb = new StringBuilder();

        sb.append("@").append(nombre)
          .append("(").append(capacidadAmp).append(") [");

        boolean primero = true;

        for (Enlace e : enlacesSalientes.values()) {

            if (!primero) {
                sb.append(", ");
            }

            sb.append(e.toString());
            primero = false;
        }

        sb.append("]");

        return sb.toString();
    }
}
