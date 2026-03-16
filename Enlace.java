package redSocial;

/**
 * Clase que representa un enlace unidireccional entre dos usuarios.
 * Un enlace permite difundir mensajes con un coste de propagación asociado.
 * 
 * @author Jaime Garcia, Jean del Pozo
 * @version 1.0
 */
public class Enlace {
    /** Usuario que actúa como origen del enlace. */
    private Usuario usuarioOrigen;
    
    /** Usuario que actúa como destino del enlace. */
    private Usuario usuarioDestino;
    
    /** Valor del coste de propagación del enlace. */
    private int coste;
    
    /** Variable estática que acumula la suma de los costes de todos los enlaces creados. */
    private static int sumaDeCostes = 0;

    /**
     * Constructor que inicializa un enlace con origen, destino y un coste específico.
     * Si el coste indicado es menor o igual a cero, se asigna 1 automáticamente.
     * 
     * @param uOrigen Usuario desde el que parte el enlace.
     * @param uDestino Usuario al que llega el enlace.
     * @param coste Valor entero del coste de propagación.
     */
    public Enlace(Usuario uOrigen, Usuario uDestino, int coste) {
        this.usuarioOrigen = uOrigen;
        this.usuarioDestino = uDestino;
        if (coste <= 0) {
            this.coste = 1;
        } else {
            this.coste = coste;
        }
        // Se suma el coste de la nueva instancia al atributo estático
        Enlace.sumaDeCostes += this.coste;
    }

    /**
     * Constructor que crea un enlace con un coste por defecto de 1.
     * 
     * @param uOrigen Usuario desde el que parte el enlace.
     * @param uDestino Usuario al que llega el enlace.
     */
    public Enlace(Usuario uOrigen, Usuario uDestino) {
        this(uOrigen, uDestino, 1);
    }

    /**
     * Obtiene el usuario origen del enlace.
     * 
     * @return El objeto Usuario origen.
     */
    public Usuario getUsuarioOrigen() {
        return this.usuarioOrigen;
    }

    /**
     * Obtiene el usuario destino del enlace.
     * 
     * @return El objeto Usuario destino.
     */
    public Usuario getUsuarioDestino() {
        return this.usuarioDestino;
    }

    /**
     * Obtiene el coste de propagación del enlace.
     * 
     * @return El valor entero del coste.
     */
    public int getCoste() {
        return this.coste;
    }

    /**
     * Cambia el usuario destino y el coste del enlace, actualizando el total global.
     * Si el nuevo coste es menor o igual a cero, se tratará como 1.
     * 
     * @param nuevoUsuario El nuevo usuario de destino.
     * @param nuevoCoste El nuevo valor de coste para el enlace.
     */
    public void cambiarDestino(Usuario nuevoUsuario, int nuevoCoste) {
        this.usuarioDestino = nuevoUsuario;
        // Para actualizar la suma total de costes, restamos el coste antiguo y sumamos el nuevo. Si es menor o igual a 0 se cuenta como 1
        if (nuevoCoste > 0) {
            Enlace.sumaDeCostes = Enlace.sumaDeCostes - this.coste + nuevoCoste;
            this.coste = nuevoCoste;
        } else {
            Enlace.sumaDeCostes = Enlace.sumaDeCostes - this.coste + 1;
            this.coste = 1;
        }
    }

    /**
     * Devuelve el total acumulado de los costes de todos los enlaces creados.
     * 
     * @return La suma total de los costes como un entero.
     */
    public static int getSumaDeCostes() {
        return Enlace.sumaDeCostes;
    }

    /**
     * Devuelve el coste especial del enlace (por defecto 0).
     * 
     * @return El valor 0 para esta clase básica.
     */
    public int costeEspecial() {
        return 0;
    }

    /**
     * Calcula el coste real del enlace sumando el coste base y el especial.
     * 
     * @return La suma del coste y el coste especial.
     */
    public int costeReal() {
        return this.coste + costeEspecial();
    }

    /**
     * Devuelve una cadena con la representación concisa del enlace.
     * Formato: (@origen--coste-->@destino)
     * 
     * @return Una cadena de texto descriptiva del enlace.
     */
    @Override
    public String toString() {
        return "(@" + usuarioOrigen.getNombre() + "--" + this.coste + "-->@" + usuarioDestino.getNombre() + ")";
    }
}
