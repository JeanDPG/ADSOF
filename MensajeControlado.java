package redSocial;

public class MensajeControlado extends Mensaje{
        int rigidez;

    public MensajeControlado(String contenido, int alcanceDisponible, Usuario usuarioActual, int rigidez) {
        super(contenido, alcanceDisponible, usuarioActual);
        this.rigidez = rigidez;
    }

    @Override
    public boolean puedeDifundirPor(Enlace e) {
        if(e instanceof EnlaceSeñuelo)return false;
        return true;
    }

    @Override
    public boolean aceptadoPor(Usuario u) {
        Exposicion exp = u.getExposicion();

        return switch (exp) {
            case OCULTA -> true;
            case BAJA   -> this.rigidez >= 5;
            case MEDIA  -> this.rigidez >= 10;
            case ALTA   -> this.rigidez >= 20;
            case VIRAL  -> this.rigidez >= 50;
            default     -> false;
        };
    }
}
