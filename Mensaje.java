package redSocial;

/**
 * Representa un mensaje que se difunde a través de la red social.
 * Contiene el texto, el alcance disponible y realiza el seguimiento del usuario actual.
 * 
 * @author Jaime Garcia, Jean del Pozo
 * @version 1.0
 */
public class Mensaje {
    /** Contenido textual del mensaje. */
    private String contenido;
    
    /** Capacidad de propagación restante del mensaje. */
    private int alcanceDisponible;
    
    /** Usuario donde se encuentra el mensaje en el instante actual. */
    private Usuario usuarioActual;

    /**
     * Constructor para inicializar los atributos de un mensaje.
     * 
     * @param contenido Texto que contiene el mensaje.
     * @param alcanceDisponible Unidades de alcance iniciales.
     * @param usuarioActual Usuario emisor inicial del mensaje.
     */
    public Mensaje(String contenido, int alcanceDisponible, Usuario usuarioActual) {
        this.contenido = contenido;
        this.alcanceDisponible = alcanceDisponible;
        this.usuarioActual = usuarioActual;
    }

    /**
     * Obtiene el contenido textual del mensaje.
     * 
     * @return El texto del mensaje.
     */
    public String getContenido() {
        return this.contenido;
    }

    /**
     * Obtiene el alcance disponible actual del mensaje.
     * 
     * @return El valor entero del alcance.
     */
    public int getAlcanceDisponible() {
        return this.alcanceDisponible;
    }

    /**
     * Obtiene el usuario en el que se encuentra el mensaje actualmente.
     * 
     * @return El objeto Usuario actual.
     */
    public Usuario getUsuarioActual() {
        return this.usuarioActual;
    }

    /**
     * Intenta difundir el mensaje a través de un enlace específico.
     * Si la difusión es posible, actualiza el alcance, el historial del emisor y traslada el mensaje.
     * 
     * @param e Enlace por el que se intenta realizar la transmisión.
     * @return true si la difusión se realizó con éxito, false en caso contrario.
     */
    public boolean difunde(Enlace e) {

        if (e != null && puedeDifundirPor(e) && e.getUsuarioOrigen().equals(usuarioActual) && aceptadoPor(e.getUsuarioDestino())) {
           if(e instanceof EnlaceSeñuelo){
               if(((EnlaceSeñuelo) e).getProbRetorno() > generarNumeroAleatorio()) {
                   this.usuarioActual = e.getUsuarioOrigen();
                   System.out.println(this);
                   return true;
               }
           }
            Usuario destino = e.getUsuarioDestino();
            Usuario origen = e.getUsuarioOrigen();
            alcanceDisponible = alcanceDisponible - e.costeReal();
            if(alcanceDisponible < 1) return false;
            alcanceDisponible = alcanceDisponible + destino.getCapacidadAmp();
            origen.getHistorialMensajes().add(this);
            origen.ajustarExposicion(this);
            usuarioActual = destino;
            System.out.println(this);

            return true;
        }

        return false;
    }
    
  
    public int generarNumeroAleatorio() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }

    /**
     * Difunde el mensaje de forma iterativa a través de una lista variable de usuarios.
     * El mensaje se trasladará siempre que exista un enlace válido y alcance suficiente.
     * 
     * @param usuarios Lista de usuarios por los que se prevé que pase el mensaje.
     * @return true solo si el mensaje pudo difundirse correctamente en todos los saltos.
     */
    public boolean difunde(Usuario... usuarios) {
        boolean todoCorrecto = true;

        for (Usuario u : usuarios) {
            Enlace e = usuarioActual.getEnlace(u);

            if (e != null) {
                if (!difunde(e)) {
                    todoCorrecto = false;
                }
            } else {
                todoCorrecto = false;
            }
        }

        return todoCorrecto;
    }

    /**
     * Resta el coste de un enlace al alcance disponible del mensaje.
     * 
     * @param enlace El enlace cuyo coste se va a restar.
     * @return El nuevo valor del alcance disponible.
     */
    public int restarCoste(Enlace enlace) {
        this.alcanceDisponible -= enlace.getCoste();
        return this.alcanceDisponible;
    }

    /**
     * Suma la capacidad de amplificación del destino de un enlace al alcance del mensaje.
     * 
     * @param enlace El enlace del que se obtiene el usuario destino para amplificar.
     */
    public void sumarAmplificacion(Enlace enlace) {
        this.alcanceDisponible += enlace.getUsuarioDestino().getCapacidadAmp();
    }

    /**
     * Comprueba si el mensaje tiene alcance suficiente para cubrir el coste del enlace.
     * 
     * @param e El enlace que se quiere evaluar.
     * @return true si el alcance es mayor o igual al coste real del enlace.
     */
    public boolean puedeDifundirPor(Enlace e) {
        return alcanceDisponible >= e.costeReal();
    }

    /**
     * Determina si un usuario acepta recibir el mensaje.
     * 
     * @param u El usuario destino.
     * @return true si el usuario acepta el mensaje (actualmente siempre true).
     */
    public boolean aceptadoPor(Usuario u) {
        // Se completará más adelante con métodos especiales. Ahora solo devuelve true.
        return true;
    }

    /**
     * Devuelve una cadena descriptiva con el estado actual del mensaje.
     * Formato: Mensaje (contenido:alcance) en @usuario
     * 
     * @return La representación textual del mensaje.
     */
    @Override
    public String toString() {
        return "Mensaje (" + contenido + ":" + this.alcanceDisponible + ") en @" + this.usuarioActual.getNombre();
    }
}
