package redSocial;

import java.util.*;

/**
 * Representa un usuario dentro de la red social.
 * Un usuario tiene un nombre, una capacidad de amplificación de mensajes,
 * una lista de enlaces salientes, un nivel de exposición y un historial.
 * 
 * @author Jaime Garcia, Jean del Pozo
 * @version 1.0
 */
public class Usuario {

    /** Nombre identificativo del usuario. */
    private String nombre;
    
    /** Unidades de influencia que otorga a un mensaje cuando le llega. */
    private int capacidadAmp;
    
    /** Colección de enlaces salientes que respeta el orden de creación. */
    private List<Enlace> enlacesSalientes;
    
    /** Nivel de visibilidad o accesibilidad para la recepción de mensajes. */
    private Exposicion exposicion;
    
    /** Registro de mensajes que han llegado al usuario. */
    private List<Mensaje> historialMensajes;

    /**
     * Constructor que recibe el nombre y asigna capacidad 2 y exposición ALTA por defecto.
     * 
     * @param nombre Nombre del usuario.
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.capacidadAmp = 2;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = Exposicion.ALTA;
    }

    /**
     * Constructor que recibe nombre y capacidad. Asigna exposición ALTA por defecto.
     * 
     * @param nombre Nombre del usuario.
     * @param capacidad Unidades de capacidad de amplificación.
     */
    public Usuario(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadAmp = capacidad;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = Exposicion.ALTA;
    }

    /**
     * Constructor que permite especificar nombre, capacidad y exposición inicial.
     * 
     * @param nombre Nombre del usuario.
     * @param capacidad Unidades de capacidad de amplificación.
     * @param exp Nivel de exposición inicial.
     */
    public Usuario(String nombre, int capacidad, Exposicion exp) {
        this.nombre = nombre;
        this.capacidadAmp = capacidad;
        this.enlacesSalientes = new ArrayList<>();
        this.historialMensajes = new ArrayList<>();
        this.exposicion = exp;
    }

    /**
     * Modifica el nivel de exposición del usuario durante la ejecución.
     * 
     * @param e Nuevo valor de exposición.
     */
    public void cambiarExposicion(Exposicion e){
        this.exposicion = e;
    }

    /**
     * Añade un enlace si el origen coincide con el usuario y el destino no es él mismo.
     * Evita duplicados hacia el mismo destino.
     * 
     * @param e Enlace a añadir.
     * @return true si se añadió correctamente, false en caso contrario.
     */
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

    /**
     * Sobrecarga de addEnlace que crea internamente el objeto Enlace.
     * 
     * @param usuarioDestino Usuario al que apunta el nuevo enlace.
     * @param coste Valor del coste del enlace.
     * @return true si el enlace se crea y añade correctamente.
     */
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

    /**
     * Busca un enlace directo desde este usuario hacia otro dado.
     * 
     * @param usuarioDestino Usuario destino que se busca.
     * @return El enlace encontrado o null si no existe conexión.
     */
    public Enlace getEnlace(Usuario usuarioDestino) {

        for (Enlace e : this.enlacesSalientes) {
            if (e.getUsuarioDestino().equals(usuarioDestino)) {
                return e;
            }

        }

        return null;
    }

    /**
     * Accede al i-ésimo enlace de la secuencia de enlaces salientes.
     * 
     * @param i Índice de la secuencia.
     * @return El enlace en dicha posición o null si el índice no es válido.
     */
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

    /**
     * Obtiene el número total de enlaces salientes.
     * 
     * @return Cantidad de enlaces.
     */
    public int getNumEnlaces() {
        return enlacesSalientes.size();
    }

    /** @return El nombre del usuario. */
    public String getNombre() {
        return nombre;
    }

    /** @return La capacidad de amplificación actual. */
    public int getCapacidadAmp() {
        return capacidadAmp;
    }

    /** @return La lista de enlaces salientes. */
    public List<Enlace> getEnlacesSalientes() {
        return enlacesSalientes;
    }

    /** @return El nivel de exposición actual. */
    public Exposicion getExposicion() {
        return exposicion;
    }

    /** @return La lista de mensajes recibidos en el historial. */
    public List<Mensaje> getHistorialMensajes() {
        return historialMensajes;
    }

    /**
     * Devuelve una representación textual del usuario y sus enlaces.
     * Formato: @nombre(capacidad) [enlaces]
     * 
     * @return Cadena con los datos del usuario.
     */
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

    /**
     * Ajusta el nivel de exposición según el alcance del mensaje recibido
     * respecto al promedio del historial.
     * 
     * @param mensaje Mensaje recibido para comparar alcance.
     */
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
