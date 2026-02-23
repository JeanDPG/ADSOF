package red_social;

public class Enlace {
	private String usuarioOrigen;
	private String usuarioDestino;
	private int coste;
	static int sumaDeCostes=0;
	
	public Enlace(String uOrigen, String uDestino, int coste) {
		this.usuarioOrigen = uOrigen;
		this.usuarioDestino = uDestino;
		if(coste<=0) {
			this.coste=1;
		}
		else {
			this.coste= coste;
		}
		//se suma el coste de la nueva instancia al atributo estatico sumaDeCostes
		this.sumaDeCostes+=this.coste;
	}
	public Enlace(String uOrigen, String uDestino) {
		this.usuarioOrigen = uOrigen;
		this.usuarioDestino = uDestino;
		this.coste=1;
		//se suma el coste de la nueva instancia al atributo estatico sumaDeCostes
		this.sumaDeCostes+=this.coste;
	}
	public String getUsuarioOrigen(){
		return this.usuarioOrigen;
	}
	public String getUsuarioDestino() {
		return this.usuarioDestino;
	}
	public int getCoste() {
		return this.coste;
	}
	public void cambiarDestino(Usuario nuevoUsuario, int nuevoCoste ) {
		this.usuarioDestino= Usuario.getUsuario();
		//para actualizar la suma total de costes en caso de cambiar el coste de un enlace, restamos el coste antiguo, y sumamos el nuevo
		this.sumaDeCostes=this.sumaDeCostes-this.coste+nuevoCoste;
		this.coste=nuevoCoste;
	}
	public int getSumaDeCostes() {
		return this.sumaDeCostes;
	}
	public int costeEspecial() {
		return 0;
	}
	public int costeReal() {
		return this.coste+ costeEspecial();
	}
	public String toString()
	{
		return "(@"+this.usuarioOrigen + "---"+this.coste+"-->@"+this.usuarioDestino+")";
	}	
	}
