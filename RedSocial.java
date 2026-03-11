package redSocial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedSocial {

    private HashMap<String,Usuario> usuarios = new HashMap<>();
    private List<Enlace> enlaces;
    private Mensaje mensajeInicial;
    private List<Usuario> caminoUsuarios;


    public RedSocial(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        this.enlaces = new ArrayList<>();
        this.caminoUsuarios = new ArrayList<>();
        readFromFiles(fusuarios, fenlaces, fmensaje);

    }

    public boolean readFromFiles(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        boolean exito = getUsuarioFromFile(fusuarios) &&
                getEnlaceFromFile(fenlaces) &&
                getMensajeFromFile(fmensaje);

        return exito && ejecutarDifusion(this.mensajeInicial, this.caminoUsuarios);

    }

    public boolean ejecutarDifusion(Mensaje mensajeInicial, List<Usuario> caminoUsuarios) {
        Enlace enlace;
        for (int i = 0; i < caminoUsuarios.size() - 1; i++) {
            //System.out.println(caminoUsuarios.size());
            String usuarioOrigen = caminoUsuarios.get(i).getNombre();
            String usuarioDestino = caminoUsuarios.get(i + 1).getNombre();
            if((enlace = enlaceExiste(usuarioOrigen,usuarioDestino)) == null) return false;
            mensajeInicial.difunde(enlace);

            System.out.println(mensajeInicial);
        }
        return true;
    }

    public Enlace enlaceExiste(String usuarioOrigen, String usuarioDestino) {
        for(Enlace enlace: this.enlaces) {
            if (enlace.getUsuarioOrigen().getNombre().equalsIgnoreCase(usuarioOrigen) && enlace.getUsuarioDestino().getNombre().equalsIgnoreCase(usuarioDestino)) {
                return enlace;
            }
        }
        return null;
    }




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
                        this.enlaces.add(enlace);
                        this.usuarios.get(origen).addEnlace(enlace);
                    }
                }
            }
        return true;
    }


    public boolean getMensajeFromFile(String path) throws IOException {
        List<Usuario> internautas = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;
            Mensaje mensaje = null;
            // LEER LA PRIMERA FILA
            linea = br.readLine();
            if (linea != null && !linea.trim().isEmpty()) {
                String[] cabecera = linea.trim().split(" ");

                String texto = cabecera[0];
                int alcanceInicial = Integer.parseInt(cabecera[1]);
                String usuarioOrigen = cabecera[2];
                // Se utiliza this.usuarios.get() para verificar que el usuario existe en el hashmap de usuarios
                Usuario user = this.usuarios.get(usuarioOrigen);
                mensaje = new Mensaje(texto,alcanceInicial,user);
                internautas.add(user);
            }
            this.mensajeInicial = mensaje;

            //LEER EL RESTO DE FILAS
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    internautas.add(this.usuarios.get(linea));
                }
            }
            this.caminoUsuarios = internautas;
    return true;
    }



}
