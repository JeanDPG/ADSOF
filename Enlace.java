package redSocial;

public class Enlace {
    private Usuario usuarioOrigen;
    private Usuario usuarioDestino;
    private int coste;
    static int sumaDeCostes=0;

    public Enlace(Usuario uOrigen, Usuario uDestino, int coste) {
        this.usuarioOrigen = uOrigen;
        this.usuarioDestino = uDestino;
        if(coste<=0) {
            this.coste=1;
        }
        else {
            this.coste= coste;
        }
        //se suma el coste de la nueva instancia al atributo estatico sumaDeCostes
        Enlace.sumaDeCostes+=this.coste;
    }
    public Enlace(Usuario uOrigen, Usuario uDestino) {
        this.usuarioOrigen = uOrigen;
        this.usuarioDestino = uDestino;
        this.coste=1;
        //se suma el coste de la nueva instancia al atributo estatico sumaDeCostes
        Enlace.sumaDeCostes+=this.coste;
    }
    public Usuario getUsuarioOrigen(){
        return this.usuarioOrigen;
    }
    public Usuario getUsuarioDestino() {
        return this.usuarioDestino;
    }
    public int getCoste() {
        return this.coste;
    }
    public void cambiarDestino(Usuario nuevoUsuario, int nuevoCoste ) {
        this.usuarioDestino= nuevoUsuario;
        //para actualizar la suma total de costes en caso de cambiar el coste de un enlace, restamos el coste antiguo, y sumamos el nuevo
        Enlace.sumaDeCostes=Enlace.sumaDeCostes-this.coste+nuevoCoste;
        this.coste=nuevoCoste;
    }
    public int getSumaDeCostes() {
        return Enlace.sumaDeCostes;
    }
    public int costeEspecial() {
        return 0;
    }
    public int costeReal() {
        return this.coste+ costeEspecial();
    }
    public String toString()
    {
        return "(@"+this.usuarioOrigen.getNombre() + "---"+this.coste+"-->@"+this.usuarioDestino.getNombre()+")";
    }
}