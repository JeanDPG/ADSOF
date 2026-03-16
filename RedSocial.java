package redSocial;

import redSocial.enlaces.Enlace;
import redSocial.mensajes.Mensaje;
import redSocial.usuarios.Usuario;

import java.io.*;
import java.util.*;

/**
 * Clase encargada de gestionar la simulación y persistencia de la red social.
 * Actúa como Fachada para la creación manual y automática de usuarios, enlaces y mensajes.
 *
 * @author Jaime Garcia, Jean del Pozo
 * @version 1.0
 */
public class RedSocial {

    /** Mapa que almacena los usuarios de la red indexados por su nombre. */
    private Map<String, Usuario> usuarios;

    /** Lista que registra todos los enlaces creados en el sistema. */
    private List<Enlace> colaEnlaces;

    /** Mapa que asocia cada mensaje con la secuencia de usuarios que tiene previsto visitar. */
    private Map<Mensaje, List<Usuario>> mensajes;

    /**
     * Constructor por defecto que inicializa las estructuras de datos de la red.
     */
    public RedSocial() {
        this.usuarios = new HashMap<>();
        this.colaEnlaces = new ArrayList<>();
        this.mensajes = new HashMap<>();
    }

    /**
     * Constructor que carga la red desde ficheros y ejecuta la simulación inicial.
     *
     * @param fusuarios Nombre del archivo de usuarios.
     * @param fenlaces Nombre del archivo de enlaces.
     * @param fmensaje Nombre del archivo de mensajes.
     * @throws IOException Si ocurre un error en la apertura o lectura de los archivos.
     */
    public RedSocial(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        this();
        readFromFiles(fusuarios, fenlaces, fmensaje);
    }

    /**
     * Realiza la carga completa de datos desde ficheros y dispara la difusión.
     *
     * @param fusuarios Ruta del fichero de usuarios.
     * @param fenlaces Ruta del fichero de enlaces.
     * @param fmensaje Ruta del fichero de mensajes.
     * @return true tras completar la lectura y ejecución de la difusión.
     * @throws IOException Si existe un problema con los archivos.
     */
    public boolean readFromFiles(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        getUsuariosFromFile(fusuarios);
        getEnlacesFromFile(fenlaces);
        getMensajesFromFile(fmensaje);
        return ejecutarDifusion();
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Enlace> getColaEnlaces() {
        return colaEnlaces;
    }

    public Map<Mensaje, List<Usuario>> getMensajes() {
        return mensajes;
    }

    /**
     * Ejecuta la simulación de propagación para todos los mensajes cargados.
     * Recorre la secuencia de visitas prevista para cada mensaje.
     *
     * @return true al finalizar el proceso de difusión.
     */
    public boolean ejecutarDifusion() {
        for (Map.Entry<Mensaje, List<Usuario>> entrada : mensajes.entrySet()) {

            Mensaje mensajeActual = entrada.getKey();
            List<Usuario> usuarios = entrada.getValue();
            for (Usuario destino : usuarios) {
                Enlace e = mensajeActual.getUsuarioActual().getEnlace(destino);
                mensajeActual.difunde(e);
            }
        }
        return true;
    }

    /**
     * Comprueba si existe un enlace directo entre dos usuarios dados por su nombre.
     *
     * @param usuarioOrigen Nombre del usuario de origen.
     * @param usuarioDestino Nombre del usuario de destino.
     * @return El objeto Enlace encontrado o null si no existe.
     */
    public Enlace enlaceExiste(String usuarioOrigen, String usuarioDestino) {
        for(Enlace enlace: this.colaEnlaces) {
            if (enlace.getUsuarioOrigen().getNombre().equalsIgnoreCase(usuarioOrigen) && enlace.getUsuarioDestino().getNombre().equalsIgnoreCase(usuarioDestino)) {
                return enlace;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo usuario y lo añade al registro de la red social.
     *
     * @param nombre Nombre del usuario.
     * @param capacidad Unidades de capacidad de amplificación.
     */
    public void crearUsuario(String nombre, int capacidad) {
        this.usuarios.put(nombre, new Usuario(nombre, capacidad));
    }

    /**
     * Crea un enlace entre dos usuarios y lo asigna al usuario de origen.
     *
     * @param uOrigen Nombre del usuario origen.
     * @param uDestino Nombre del usuario destino.
     * @param coste Valor del coste de propagación.
     */
    public void crearEnlace(String uOrigen, String uDestino, int coste) {
        Usuario origen = usuarios.get(uOrigen);
        Usuario destino = usuarios.get(uDestino);
        if (origen != null && destino != null) {
            Enlace nuevo = new Enlace(origen, destino, coste);
            if (origen.addEnlace(nuevo)) {
                this.colaEnlaces.add(nuevo);
            }
        }
    }

    /**
     * Crea un mensaje y lo asocia con una lista de usuarios a visitar.
     *
     * @param contenido Texto del mensaje.
     * @param alcance Valor inicial del alcance.
     * @param nombreAutor Nombre del usuario que emite el mensaje.
     * @param nombresVisitas Lista de nombres de los usuarios previstos a visitar.
     */
    public void crearMensaje(String contenido, int alcance, String nombreAutor, List<String> nombresVisitas) {
        Usuario autor = usuarios.get(nombreAutor);
        if (autor != null) {
            Mensaje m = new Mensaje(contenido, alcance, autor);
            List<Usuario> visitas = new ArrayList<>();
            for (String nombre : nombresVisitas) {
                Usuario v = usuarios.get(nombre);
                if (v != null) visitas.add(v);
            }
            this.mensajes.put(m, visitas);
        }
    }

    /**
     * Lee la definición de usuarios desde un archivo de texto.
     *
     * @param path Ruta del archivo de usuarios.
     * @throws IOException Si ocurre un error de lectura.
     */
    private void getUsuariosFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length >= 2) {
                    crearUsuario(partes[0], Integer.parseInt(partes[1]));
                }
            }
        }
    }

    /**
     * Lee la definición de enlaces desde un archivo de texto.
     *
     * @param path Ruta del archivo de enlaces.
     * @throws IOException Si ocurre un error de lectura.
     */
    private void getEnlacesFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length >= 3) {
                    crearEnlace(partes[0], partes[1], Integer.parseInt(partes[2]));
                }
            }
        }
    }

    /**
     * Lee la definición de mensajes y secuencias de visitas desde un archivo de texto.
     *
     * @param path Ruta del archivo de mensajes.
     * @throws IOException Si ocurre un error de lectura.
     */
    private void getMensajesFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            Mensaje mensajeActual = null;
            List<Usuario> visitas = null;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (linea.startsWith("\"")) {
                    String[] partes = linea.split("\"");
                    String contenido = partes[1];
                    String[] resto = partes[2].trim().split("\\s+");

                    int alcance = Integer.parseInt(resto[0]);
                    Usuario autor = usuarios.get(resto[1]);

                    mensajeActual = new Mensaje(contenido, alcance, autor);
                    visitas = new ArrayList<>();
                    mensajes.put(mensajeActual, visitas);
                } else if (mensajeActual != null) {
                    Usuario u = usuarios.get(linea);
                    if (u != null) visitas.add(u);
                }
            }
        }
    }

    /**
     * Guarda la lista actual de usuarios en un archivo de texto.
     *
     * @param path Ruta del archivo de destino.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarUsuarios(String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Usuario u : usuarios.values()) {
                pw.println(u.getNombre() + " " + u.getCapacidadAmp());
            }
        }
    }

    /**
     * Guarda la lista actual de enlaces en un archivo de texto.
     *
     * @param path Ruta del archivo de destino.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarEnlaces(String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Enlace e : colaEnlaces) {
                pw.println(e.getUsuarioOrigen().getNombre() + " " +
                        e.getUsuarioDestino().getNombre() + " " + e.getCoste());
            }
        }
    }

    /**
     * Guarda los mensajes y sus secuencias de visita en un archivo de texto.
     *
     * @param path Ruta del archivo de destino.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarMensajes(String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Map.Entry<Mensaje, List<Usuario>> entrada : mensajes.entrySet()) {
                Mensaje m = entrada.getKey();
                pw.println("\"" + m.getContenido() + "\" " + m.getAlcanceDisponible() +
                        " " + m.getUsuarioActual().getNombre());
                for (Usuario visita : entrada.getValue()) {
                    pw.println(visita.getNombre());
                }
            }
        }
    }
}
