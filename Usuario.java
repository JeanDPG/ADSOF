package redSocial;

import java.util.*;

public class Usuario {

    private String nombre;
    private int capacidadAmp;
    private List<Enlace> enlacesSalientes;
    private Exposicion exposicion;
    private List<Mensaje> historialMensajes;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.capacidadAmp = 2;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = Exposicion.ALTA;
    }

    public Usuario(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadAmp = capacidad;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = Exposicion.ALTA;
    }

    public Usuario(String nombre, int capacidad, Exposicion exp) {
        this.nombre = nombre;
        this.capacidadAmp = capacidad;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = exp;
    }


    public void cambiarExposicion(Exposicion e){
        this.exposicion = e;
    }

    public boolean addEnlace(Enlace e) {
        if (e.getUsuarioOrigen().equals(this) &&
                !(e.getUsuarioDestino().equals(this))) {
            for (Enlace enlace: this.enlacesSalientes) {
                if (enlace.getUsuarioDestino().equals(e.getUsuarioDestino())) {
                    return false;
                }
            }
            this.enlacesSalientes.add(e);
            return true;
        }
        return false;
    }

    public boolean addEnlace(Usuario usuarioDestino, int coste) {
        if (!(usuarioDestino.equals(this))){
            for (Enlace enlace : this.enlacesSalientes) {
                if (enlace.getUsuarioDestino().equals(usuarioDestino)) {
                    return false;
                }
            }
            this.enlacesSalientes.add(new Enlace(this,usuarioDestino,coste));
            return true;
        }

        return false;
    }
    public Enlace getEnlace(Usuario usuarioDestino) {

        for (Enlace e : this.enlacesSalientes) {
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
        for (Enlace e : this.enlacesSalientes) {
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

    public List getEnlacesSalientes() {
        return enlacesSalientes;
    }


    public Exposicion getExposicion() {
        return exposicion;
    }

    public List<Mensaje> getHistorialMensajes() {
        return historialMensajes;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("@").append(nombre)
                .append("(").append(capacidadAmp).append(") [");

        boolean primero = true;

        for (Enlace e : this.enlacesSalientes) {

            if (!primero) {
                sb.append(", ");
            }

            sb.append(e.toString());
            primero = false;
        }

        sb.append("]");

        return sb.toString();
    }

    public void ajustarExposicion(Mensaje mensaje) {
        float media = 0;
        int elem = 0;

        for(Mensaje mensajeArray: this.getHistorialMensajes()){
            media += mensajeArray.getAlcanceDisponible();
            elem++;
        }
        media = media/elem;
        if(mensaje.getAlcanceDisponible() > media) {
            this.exposicion.siguiente();
        }else if(mensaje.getAlcanceDisponible() < media){
            this.exposicion.anterior();
        }else{

        }
    }
}
