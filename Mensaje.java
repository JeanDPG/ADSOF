package redSocial;


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

		        Usuario destino = e.getUsuarioDestino();

		        alcanceDisponible = alcanceDisponible - e.costeReal();
		        alcanceDisponible = alcanceDisponible + destino.getCapacidadAmp();

		        usuarioActual = destino;

		        return true;
		    }

		    return false;
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
	 
	 public boolean puedeDifundirPor(Enlace e) {
		    return alcanceDisponible >= e.costeReal();
		}
	 public boolean aceptadoPor(Usuario u) {
		 //Se completara mas adelante con metodos . ahora solo devuelve true
		 return true;
	 }
	 public String toString() {
		 return "Mensaje ("+contenido+":" + this.alcanceDisponible + ") en @"+ this.usuarioActual.getNombre();
	 }
}