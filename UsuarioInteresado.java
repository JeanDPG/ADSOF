package redSocial;

public class UsuarioInteresado extends Usuario{


    public UsuarioInteresado(String nombre) {
        super(nombre);
    }

    public UsuarioInteresado(String nombre, int capacidad) {
        super(nombre, capacidad);
    }

    public UsuarioInteresado(String nombre, int capacidad, Exposicion exp) {
        super(nombre, capacidad, exp);
    }

    public Enlace getEnlace(Usuario usuarioDestino) {
        Enlace enlace = null;
        for (Enlace e : this.getEnlacesSalientes()) {

            if (e.getUsuarioDestino().equals(usuarioDestino)) {
                enlace = e;
            }
            if(e.getUsuarioDestino().getExposicion().expCritica()){
                return e;
            }

        }
        return enlace;
    }
}
