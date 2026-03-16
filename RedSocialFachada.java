package redSocial;


import java.io.*;
import java.util.List;
import java.util.Map;

public class RedSocialFachada extends RedSocial{
    public RedSocialFachada()  {

    }

    /**
     * Crea un usuario estándar y lo añade al mapa de usuarios de la red.
     * @param nombre Identificador único del usuario.
     * @param capacidad Capacidad de amplificación del usuario.
     */
    public void crearUsuario(String nombre, int capacidad){
        this.getUsuarios().put(nombre,new Usuario(nombre, capacidad));
    }
    
    /**
     * Crea un usuario con una exposición inicial específica.
     * @param nombre Identificador único del usuario.
     * @param capacidad Capacidad de amplificación.
     * @param exp Objeto Exposición asociado al usuario.
     */
    public void crearUsuario(String nombre, int capacidad, Exposicion exp){
        this.getUsuarios().put(nombre,new Usuario(nombre, capacidad, exp));
    }

    /**
     * Instancia un UsuarioInteresado, que posee una lógica de reacción específica a mensajes.
     * @param nombre Identificador único del usuario.
     * @param capacidad Capacidad de amplificación.
     * @param exp Exposición de interés para el usuario.
     */
    public void crearUsuarioInteresado(String nombre, int capacidad, Exposicion exp){
        this.getUsuarios().put(nombre,new UsuarioInteresado(nombre, capacidad, exp));
    }

    /**
     * Crea un enlace simple entre dos usuarios y lo registra tanto en la cola 
     * general como en la lista de adyacencia del usuario origen.
     * @param uOrigen Nombre del usuario que envía.
     * @param uDestino Nombre del usuario que recibe.
     * @param coste Valor numérico asociado al envío por este enlace.
 */
    public void crearEnlace(String uOrigen, String uDestino, int coste){
        Enlace enlace = new Enlace(this.getUsuarios().get(uOrigen) , this.getUsuarios().get(uDestino), coste);
        this.getColaEnlaces().add(enlace);
        this.getUsuarios().get(uOrigen).addEnlace(enlace);
    }

    /**
     * Crea un EnlaceSeñuelo, un tipo de conexión con probabilidad de retorno.
     * @param uOrigen Usuario emisor.
     * @param uDestino Usuario receptor.
     * @param coste Coste de transmisión.
     * @param probRetorno Porcentaje de probabilidad de que el mensaje regrese.
     */
    public void crearEnlace(String uOrigen, String uDestino, int coste, int probRetorno){
        EnlaceSeñuelo enlace = new EnlaceSeñuelo(this.getUsuarios().get(uOrigen) , this.getUsuarios().get(uDestino), coste, probRetorno);
        this.getColaEnlaces().add(enlace);
        this.getUsuarios().get(uOrigen).addEnlace(enlace);
    }

    /**
     * Genera un mensaje estándar y define su grupo inicial de destinatarios.
     * @param contenido Texto del mensaje.
     * @param alcanceDisponible Puntos de alcance antes de que el mensaje expire.
     * @param uOrigin Usuario que crea el mensaje.
     * @param usuarios Lista de usuarios que reciben el mensaje inicialmente.
     */
    public void crearMensaje(String contenido, int alcanceDisponible, Usuario uOrigin, List<Usuario> usuarios){
        Mensaje mensaje = new Mensaje(contenido, alcanceDisponible, uOrigin);
        this.getMensajes().put(mensaje, usuarios);
    }

    /**
     * Crea un MensajeControlado, cuya propagación está limitada por un factor de rigidez.
     * @param rigidez Valor que dificulta o condiciona la retransmisión del mensaje.
 */
    public void crearMensaje(String contenido, int alcanceDisponible, Usuario uOrigin, List<Usuario> usuarios, int rigidez){
        MensajeControlado mensaje = new MensajeControlado(contenido, alcanceDisponible, uOrigin, rigidez);
        this.getMensajes().put(mensaje, usuarios);
    }

     /**
         * Exporta la relación de usuarios actuales a un archivo de texto plano.
         * @param usuarios Mapa de usuarios a exportar.
         * @return true si la operación finaliza con éxito.
         * @throws IOException Si ocurre un error de acceso al archivo o el directorio 'data' no existe.
    */
    public boolean escrituraUsuarios(Map<String,Usuario> usuarios) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/ESCRITURA_USUARIOS.txt"));

        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            Usuario u = entry.getValue();

            bw.write(u.getNombre() + " " + u.getCapacidadAmp());
            bw.newLine(); // Salto de línea para el siguiente usuario
        }
        bw.close();
        return true;
    }

    /**
     * Guarda todos los enlaces de la red en un formato legible por columna.
     * @param colaEnlaces Lista de enlaces a persistir.
      * @return true si la operación finaliza con éxito.
        * @throws IOException Si ocurre un error de acceso al archivo o el directorio 'data' no existe.
     */
    public boolean escrituraEnlaces(List<Enlace> colaEnlaces) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/ESCRITURA_ENLACES.txt"));
        for(Enlace enlace: colaEnlaces){
            bw.write(enlace.getUsuarioOrigen().getNombre() + " " + enlace.getUsuarioDestino().getNombre() + " " + enlace.getCoste());
            bw.newLine();
        }
        bw.close();
        return true;
    }

    /**
     * Genera un reporte detallado de los mensajes y sus listas de destinatarios asociados.
     * @param mensajes Mapa que vincula cada mensaje con sus receptores.
      * @return true si la operación finaliza con éxito.
        * @throws IOException Si ocurre un error de acceso al archivo o el directorio 'data' no existe.
     */
    public boolean escrituraMensajes(Map<Mensaje,List<Usuario>> mensajes) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/ESCRITURA_MENSAJES.txt"));
        for (Map.Entry<Mensaje, List<Usuario>> entry : mensajes.entrySet()) {
            Mensaje m = entry.getKey();
            List<Usuario> listaDestinatarios = entry.getValue();

            bw.write(m.getContenido() + " " + m.getAlcanceDisponible() + " " + m.getUsuarioActual().getNombre());
            bw.newLine();
            for (Usuario user: listaDestinatarios) {
                bw.write(user.getNombre());
                bw.newLine();
            }

            bw.newLine();
        }
        bw.close();
        return true;
    }


}
