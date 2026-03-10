package red_social;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public class RedSocial {

    private HashMap<String,Usuario> usuarios = new HashMap<>();
    private List<Enlace> enlaces;
    private String fusuarios;
    private String fenlaces;
    private String fmensaje;
    private Mensaje mensajeInicial;
    private List<String> caminoUsuarios;


    public RedSocial(String fusuarios, String fenlaces, String fmensaje){
       this.fusuarios = fusuarios;
       this.fenlaces = fenlaces;
       this.fmensaje = fmensaje;
       this.caminoUsuarios = new ArrayList<>();

    }

    public boolean readFromFiles(String fusuarios, String fenlaces, String fmensaje) throws IOException {
        boolean exito = getUsuarioFromFile(fusuarios) &&
                getEnlaceFromFile(fenlaces) &&
                getMensajeFromFile(fmensaje);

        return exito && ejecutarDifusion(this.mensajeInicial, this.caminoUsuarios);

    }

    public boolean ejecutarDifusion(Mensaje mensajeInicial, List<String> caminoUsuarios) {
        Enlace enlace;
        for (int i = 0; i < caminoUsuarios.size() - 1; i++) {
            String usuarioOrigen = caminoUsuarios.get(i);
            String usuarioDestino = caminoUsuarios.get(i + 1);
            if((enlace = enlaceExiste(usuarioOrigen,usuarioDestino)) == null) return false;
            if(restarCoste(enlace) < 0) return false;
            sumarAmplificacion(enlace);
            System.out.println();
        }
        return true;
    }

    private Enlace enlaceExiste(String usuarioOrigen, String usuarioDestino) {
        for(Enlace enlace: this.enlaces) {
            if (enlace.getUsuarioOrigen().equalsIgnoreCase(usuarioOrigen) && enlace.getUsuarioDestino().equalsIgnoreCase(usuarioDestino)) {
                return enlace;
            }
        }
        return null;
    }

    public int restarCoste(Enlace enlace){
        this.mensajeInicial.alcanceDisponible -= enlace.getCoste();
        return this.mensajeInicial.alcanceDisponible;
    }

    public void sumarAmplificacion(Enlace enlace){
        this.mensajeInicial.alcaceDisponible += enlace.getUsuarioDestino().getCapacidadAmplificacion();
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
        //ArrayList enlaces = new ArrayList();

        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!(linea.isEmpty())) {
                    String[] partes = linea.split(" ");

                    if (partes.length >= 3) {
                        String origen = partes[0];
                        String destino = partes[1];
                        int coste = Integer.parseInt(partes[3]);
                        Enlace enlace = new Enlace(origen, destino, coste);
                        this.usuarios.get(origen).addEnlace(enlace);
                    }
                }
            }
        return true;
    }


    public boolean getMensajeFromFile(String path) throws IOException {
        List<String> internautas = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;
            Mensaje mensaje = null;
            // LEER LA PRIMERA FILA
            linea = br.readLine();
            if (linea != null && !linea.trim().isEmpty()) {
                String[] cabecera = linea.trim().split(" ");

                String texto = cabecera[0];
                String alcanceInicial = cabecera[1];
                String usuarioOrigen = cabecera[2];
                mensaje = new Mensaje(texto,alcanceInicial,usuarioOrigen);

            }
            this.mensajeInicial = mensaje;

            //LEER EL RESTO DE FILAS
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    internautas.add(linea);
                }
            }
            this.caminoUsuarios = internautas;
    return true;
    }

    @Override
    public String toString() {

    }
}
