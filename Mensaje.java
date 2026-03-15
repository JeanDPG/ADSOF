package redSocial;


import java.util.concurrent.ThreadLocalRandom;

public class Mensaje {
    private String contenido;
    private int alcanceDisponible;
    private Usuario usuarioActual;
    public Mensaje(String contenido, int alcanceDisponible, Usuario usuarioActual ) {
        this.contenido=contenido;
        this.alcanceDisponible=alcanceDisponible;
        this.usuarioActual=usuarioActual;
    }
    public String getContenido() {
        return this.contenido;
    }
    public int getAlcanceDisponible(){
        return this.alcanceDisponible;
    }
    public Usuario getUsuarioActual() {
        return this.usuarioActual;
    }

    public boolean difunde(Enlace e) {

        if (e != null && puedeDifundirPor(e) && aceptadoPor(e.getUsuarioDestino())) {
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


    public int restarCoste(Enlace enlace){
        this.alcanceDisponible -= enlace.getCoste();
        return this.alcanceDisponible;
    }

    public void sumarAmplificacion(Enlace enlace){
        this.alcanceDisponible += enlace.getUsuarioDestino().getCapacidadAmp();
    }

    public boolean puedeDifundirPor(Enlace e) {

        return alcanceDisponible >= e.costeReal();
    }
    public boolean aceptadoPor(Usuario u) {
        return true;
    }
    @Override
    public String toString() {
        return "Mensaje ("+contenido+":" + this.alcanceDisponible + ") en @"+ this.usuarioActual.getNombre();
    }
}