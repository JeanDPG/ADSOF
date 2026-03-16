package redSocial;

/**
 * Tipo de mensaje con restricciones de difusión basadas en el tipo de enlace
 * y en un nivel de rigidez frente a la exposición del usuario.
 */
public class MensajeControlado extends Mensaje {
    
    int rigidez; // Umbral de resistencia para ser aceptado por los usuarios

    /**
     * Constructor para mensajes con control de rigidez.
     */
    public MensajeControlado(String contenido, int alcanceDisponible, Usuario usuarioActual, int rigidez) {
        super(contenido, alcanceDisponible, usuarioActual);
        this.rigidez = rigidez;
    }

    /**
     * Define si el mensaje puede viajar a través de un enlace.
     * Restringe el paso si el enlace es de tipo EnlaceSeñuelo.
     * @return false si es un EnlaceSeñuelo, true en caso contrario.
     */
    @Override
    public boolean puedeDifundirPor(Enlace e) {
        if (e instanceof EnlaceSeñuelo) return false;
        return true;
    }

    /**
     * Valida si un usuario acepta el mensaje comparando su nivel de exposición
     * con el valor de rigidez del mensaje.
     * @param u Usuario que recibe el mensaje.
     * @return true si la rigidez cumple el umbral del nivel de exposición.
     */
    @Override
    public boolean aceptadoPor(Usuario u) {
        Exposicion exp = u.getExposicion();

        return switch (exp) {
            case OCULTA -> true;            // Siempre aceptado si es oculta
            case BAJA   -> this.rigidez >= 5;
            case MEDIA  -> this.rigidez >= 10;
            case ALTA   -> this.rigidez >= 20;
            case VIRAL  -> this.rigidez >= 50;
            default     -> false;
        };
    }
}
