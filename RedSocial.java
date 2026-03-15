package redSocial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedSocial {

    private Map<String,Usuario> usuarios =  new HashMap<>();
    private List<Enlace> colaEnlaces = new ArrayList<>();
    private Map<Mensaje,List<Usuario>> mensajes = new HashMap<>();

    public RedSocial(){

    }

    public RedSocial(String fusuarios, String fenlaces, String fmensaje) throws IOException {

        readFromFiles(fusuarios, fenlaces, fmensaje);

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

    public boolean readFromFiles(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        boolean exito = getUsuarioFromFile(fusuarios) &&
                getEnlaceFromFile(fenlaces) &&
                getMensajeFromFile(fmensaje);

        return exito && ejecutarDifusion(this.mensajes);

    }

    public boolean ejecutarDifusion(Map<Mensaje, List<Usuario>> mensajes) {
        for (Map.Entry<Mensaje, List<Usuario>> entrada : mensajes.entrySet()) {

            Mensaje mensajeActual = entrada.getKey();
            List<Usuario> usuarios = entrada.getValue();
            if(!mensajeActual.difunde(usuarios.toArray(new Usuario[0]))) return false;
        }
        return true;

    }

//    public Enlace enlaceExiste(String usuarioOrigen, String usuarioDestino) {
//        for(Enlace enlace: this.colaEnlaces) {
//            if (enlace.getUsuarioOrigen().getNombre().equalsIgnoreCase(usuarioOrigen) && enlace.getUsuarioDestino().getNombre().equalsIgnoreCase(usuarioDestino)) {
//                return enlace;
//            }
//        }
//        return null;
//    }



    public boolean getUsuarioFromFile(String path) throws IOException {
        HashMap<String,Usuario> users = new HashMap();
        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!(linea.isEmpty())) {

                    String[] partes = linea.split(" ");

                    if (partes.length >= 2) {
                        String nombre = partes[0];
                        int capacidad = Integer.parseInt(partes[1]);
                        Usuario user = new Usuario(nombre, capacidad);
                        users.put(nombre,user);
                    }
                }
            }
            this.usuarios = users;
        return true;
    }

    public boolean getEnlaceFromFile(String path) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!(linea.isEmpty())) {
                    String[] partes = linea.split(" ");

                    if (partes.length >= 2) {
                        String origen = partes[0];
                        String destino = partes[1];
                        int coste = Integer.parseInt(partes[2]);
                        Usuario userOrigen = this.usuarios.get(origen);
                        Usuario userDestino = this.usuarios.get(destino);
                        Enlace enlace = new Enlace(userOrigen, userDestino, coste);
                        this.colaEnlaces.add(enlace);
                        this.usuarios.get(origen).addEnlace(enlace);
                    }
                }
            }
        return true;
    }


    public boolean getMensajeFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea;
        Mensaje mensajeActual = null;
        List<Usuario> internautas = null;
        // Si la linea devuelve null, es EOF
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            //Una linea vacia no devulve null, sino "" (un String de longitud 0)
            if (linea.isEmpty()) continue;

            if (linea.startsWith("\"")) {
                // Guardar el anterior antes de empezar el nuevo
                if (mensajeActual != null) this.mensajes.put(mensajeActual, internautas);

                // Procesar cabecera "HOLA" 10 ana
                String[] partes = linea.split("\"");
                String[] resto = partes[2].trim().split(" ");

                Usuario origen = this.usuarios.get(resto[1]);
                mensajeActual = new Mensaje(partes[1], Integer.parseInt(resto[0]), origen);

                internautas = new ArrayList<>();
                internautas.add(origen);
            } else {
                // Es un usuario de la lista
                internautas.add(this.usuarios.get(linea));
            }
        }
        // Guardar el último que quedó en el bucle
        if (mensajeActual != null) this.mensajes.put(mensajeActual, internautas);

        br.close();
        return true;
    }



}
