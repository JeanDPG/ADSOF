import java.util.ArrayList;
import java.util.List;

/**
 * Clase BibliotecaApp que contiene el main para probar las funciones de libros
 * @author Jaime García González y Jean del Pozo Gómez
 *
 */
public class BibliotecaApp {
    
    public static void main(String[] args) {
        ArrayList<Libro> libros = new ArrayList<> (List.of(
            new Libro("1", "El Quijote", "Miguel de Cervantes", 5, 1600, "Novela"),
            new Libro("2", "El Murcielago", "Jo Nesbo", 1, 1950, "Autoayuda"),
            new Libro("3", "Learn Java", "David Hoffman", 6, 2004, "Programacion")
        ));
        libros.get(1).prestar();
        for (Libro l : libros) {
            System.out.println(l);
        }

        libros.get(1).devolver();
        System.out.println(libros);

        libros.add(new Libro("4", "Con viento solano", "Ignacio Aldecoa", 1, 1920, "Ficcion"));
        System.out.println(libros);
    }
}
