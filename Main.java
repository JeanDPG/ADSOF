package redSocial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPruebasApartado6 {
    public static void main(String[] args)  {


            /*Usuario ana    = new Usuario("ana", 1);       // capacidad de amplificación 1
            Usuario luis   = new Usuario("luis", 5);
            Usuario carmen = new Usuario("carmen");       // por defecto capacidad 2
            Mensaje m = new Mensaje("Hi!", 50, ana);      // texto (Hi!) 50 unid. alcance inicial, msj en ana
            ana.addEnlace(new Enlace(ana, luis, 68));
            ana.addEnlace(carmen, 33);
            System.out.println(m);
            m.difunde(luis, carmen);    // irá directamente a @carmen
            System.out.println(m);      // alcance 19 = 50 - 33 + 2
            carmen.addEnlace(new Enlace(carmen, luis, 11));
            m.difunde(carmen.getEnlace(luis));
            System.out.println(m);      // en @luis con alcance 13 = 19 - 11 + 5
            */


        //-----------    PRUEBA RED SOCIAL   -------------

        System.out.println("PRUEBA RED SOCIAL");
      
        try {
            RedSocial rs = new RedSocial("data/USUARIOS.txt", "data/ENLACES.txt", "data/MENSAJE.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //-----------    PRUEBA RED SOCIAL FACHADA    -------------
        System.out.println();
        System.out.println("PRUEBA RED SOCIAL FACHADA");

        RedSocialFachada rdf = new RedSocialFachada();

        // 1. Usuarios
        rdf.crearUsuario("Ana", 3, Exposicion.MEDIA);
        rdf.crearUsuario("Luis", 4,  Exposicion.MEDIA);
        rdf.crearUsuarioInteresado("Laura", 2,  Exposicion.MEDIA);
        rdf.crearUsuario("Quique", 2,  Exposicion.MEDIA);
        rdf.crearUsuario("Jean", 1, Exposicion.MEDIA);
        rdf.crearUsuario("Daniel", 2,  Exposicion.MEDIA);
        rdf.crearUsuario("Maria", 5, Exposicion.MEDIA);

        //2. Enlaces
        rdf.crearEnlace("Ana", "Luis", 10);
        rdf.crearEnlace("Luis", "Laura", 4);
        rdf.crearEnlace("Luis", "Jean", 5);
        rdf.crearEnlace("Laura", "Quique", 3, 10);
        rdf.crearEnlace("Quique", "Jean", 6);
        rdf.crearEnlace("Laura", "Jean", 8);
        rdf.crearEnlace("Quique", "Daniel", 9);
        rdf.crearEnlace("Jean", "Daniel", 6);
        rdf.crearEnlace("Daniel", "Maria", 5);


        // 3. Recoger usuarios del diccionario
        Usuario userAna = rdf.getUsuarios().get("Ana");
        Usuario userLuis = rdf.getUsuarios().get("Luis");
        Usuario userLaura = rdf.getUsuarios().get("Laura");
        Usuario userQuique = rdf.getUsuarios().get("Quique");
        Usuario userJean = rdf.getUsuarios().get("Jean");
        Usuario userDaniel = rdf.getUsuarios().get("Daniel");
        Usuario userMaria = rdf.getUsuarios().get("Maria");

        // Mensaje 1: Normal
        List<Usuario> receptores1 = new ArrayList();
        receptores1.add(userLuis);
        receptores1.add(userLaura);
        receptores1.add(userQuique);
        receptores1.add(userJean);
        rdf.crearMensaje("Hola!", 22, userAna, receptores1);

        // Mensaje 2: MensajeControlado
        List<Usuario> receptores2 = new ArrayList();
        receptores2.add(userLuis);
        receptores2.add(userJean);
        receptores2.add(userDaniel);
        receptores2.add(userMaria);
        rdf.crearMensaje("Hola2!", 20, userAna, receptores2, 55);

        try {
            rdf.escrituraUsuarios(rdf.getUsuarios());
            rdf.escrituraEnlaces(rdf.getColaEnlaces());
            rdf.escrituraMensajes(rdf.getMensajes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rdf.ejecutarDifusion(rdf.getMensajes());



    }
}
