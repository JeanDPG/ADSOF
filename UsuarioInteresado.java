package redSocial;

/**
 * Tipo de usuario que prioriza la conexión con usuarios en niveles críticos de exposición.
 */
public class UsuarioInteresado extends Usuario {

    /**
     * Constructores que delegan la inicialización a la clase padre Usuario.
     */
    public UsuarioInteresado(String nombre) {
        super(nombre);
    }

    public UsuarioInteresado(String nombre, int capacidad) {
        super(nombre, capacidad);
    }

    public UsuarioInteresado(String nombre, int capacidad, Exposicion exp) {
        super(nombre, capacidad, exp);
    }

    /**
     * Busca un enlace saliente hacia un destino específico.
     * Prioriza devolver inmediatamente cualquier enlace cuyo destino tenga una exposición crítica.
     * * @param usuarioDestino El usuario objetivo de la búsqueda.
     * @return El enlace hacia un usuario con exposición crítica si existe; 
     * si no, el enlace al usuarioDestino solicitado; o null si no hay coincidencia.
     */
    public Enlace getEnlace(Usuario usuarioDestino) {
        Enlace enlace = null;
        for (Enlace e : this.getEnlacesSalientes()) {

            // Si el destino coincide, lo guardamos como opción secundaria
            if (e.getUsuarioDestino().equals(usuarioDestino)) {
                enlace = e;
            }
            
            // Prioridad absoluta: Si el destino del enlace tiene exposición ALTA o VIRAL
            if(e.getUsuarioDestino().getExposicion().expCritica()){
                return e;
            }
        }
        return enlace;
    }
}
