package redSocial;


import java.io.*;
import java.util.List;
import java.util.Map;

public class RedSocialFachada extends RedSocial{
    public RedSocialFachada()  {

    }

    public void crearUsuario(String nombre, int capacidad){
        this.getUsuarios().put(nombre,new Usuario(nombre, capacidad));
    }

    public void crearUsuario(String nombre, int capacidad, Exposicion exp){
        this.getUsuarios().put(nombre,new Usuario(nombre, capacidad, exp));
    }

    public void crearUsuarioInteresado(String nombre, int capacidad, Exposicion exp){
        this.getUsuarios().put(nombre,new UsuarioInteresado(nombre, capacidad, exp));
    }

    public void crearEnlace(String uOrigen, String uDestino, int coste){
        Enlace enlace = new Enlace(this.getUsuarios().get(uOrigen) , this.getUsuarios().get(uDestino), coste);
        this.getColaEnlaces().add(enlace);
        this.getUsuarios().get(uOrigen).addEnlace(enlace);
    }

    public void crearEnlace(String uOrigen, String uDestino, int coste, int probRetorno){
        EnlaceSeñuelo enlace = new EnlaceSeñuelo(this.getUsuarios().get(uOrigen) , this.getUsuarios().get(uDestino), coste, probRetorno);
        this.getColaEnlaces().add(enlace);
        this.getUsuarios().get(uOrigen).addEnlace(enlace);
    }

    public void crearMensaje(String contenido, int alcanceDisponible, Usuario uOrigin, List<Usuario> usuarios){
        Mensaje mensaje = new Mensaje(contenido, alcanceDisponible, uOrigin);
        this.getMensajes().put(mensaje, usuarios);
    }

    public void crearMensaje(String contenido, int alcanceDisponible, Usuario uOrigin, List<Usuario> usuarios, int rigidez){
        MensajeControlado mensaje = new MensajeControlado(contenido, alcanceDisponible, uOrigin, rigidez);
        this.getMensajes().put(mensaje, usuarios);
    }



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

    public boolean escrituraEnlaces(List<Enlace> colaEnlaces) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/ESCRITURA_ENLACES.txt"));
        for(Enlace enlace: colaEnlaces){
            bw.write(enlace.getUsuarioOrigen().getNombre() + " " + enlace.getUsuarioDestino().getNombre() + " " + enlace.getCoste());
            bw.newLine();
        }
        bw.close();
        return true;
    }

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
