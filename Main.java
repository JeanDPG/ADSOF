package redSocial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
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
        //-----------    PRUEBA RED SOCIAL FACHADA    -------------

        RedSocialFachada s = new RedSocialFachada();

        // 1. Usuarios
        s.crearUsuario("Ana", 3, Exposicion.MEDIA);
        s.crearUsuario("Luis", 5,  Exposicion.BAJA);
        s.crearUsuarioInteresado("Laura", 2,  Exposicion.ALTA);
        s.crearUsuario("Quique", 2,  Exposicion.MEDIA);
        s.crearUsuario("Jean", 2, Exposicion.ALTA);

        //2. Enlaces
        s.crearEnlace("Ana", "Luis", 10);
        s.crearEnlace("Luis", "Laura", 4);
        s.crearEnlace("Laura", "Quique", 3);
        s.crearEnlace("Quique", "Jean", 6);
        s.crearEnlace("Laura", "Jean", 8);

        // 3. Mensaje
        Usuario userAna = s.getUsuarios().get("Ana");
        Usuario userLuis = s.getUsuarios().get("Luis");
        Usuario userLaura = s.getUsuarios().get("Laura");
        Usuario userQuique = s.getUsuarios().get("Quique");
        Usuario userJean = s.getUsuarios().get("Jean");

        // Mensaje 1: De Ana para una lista
        List<Usuario> receptores1 = new ArrayList();
        receptores1.add(userLuis);
        receptores1.add(userLaura);
        receptores1.add(userQuique);
        receptores1.add(userJean);
        //s.crearMensaje("Hola!", 22, userAna, receptores1);

        // Mensaje 2: De Ana para una lista
        List<Usuario> receptores2 = new ArrayList();
        receptores2.add(userLuis);
        receptores2.add(userLaura);
        receptores2.add(userQuique);
        receptores2.add(userJean);
        s.crearMensaje("Hola!", 22, userAna, receptores2, 25);

        try {
            s.escrituraUsuarios(s.getUsuarios());
            s.escrituraEnlaces(s.getColaEnlaces());
            s.escrituraMensajes(s.getMensajes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        s.ejecutarDifusion(s.getMensajes());



    }
}