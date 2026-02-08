
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase main
 * 
 * @author Jaime García González y Jean del Pozo Gómez
 *
 */
public class DemoBiblioteca {
    public static void main(String[] args) {


                // --- GÉNERO: TERROR ---
        Libro terror1 = new Libro("1", "It", "Stephen King", 5, 1986, "Terror");
        Libro terror2 = new Libro("2", "Drácula", "Bram Stoker", 3, 1897, "Terror");
        Libro terror3 = new Libro("3", "El resplandor", "Stephen King", 4, 1977, "Terror");

        // --- GÉNERO: CIENCIA FICCIÓN ---
        Libro ciencia1 = new Libro("4", "Dune", "Frank Herbert", 2, 1965, "Ciencia Ficción");
        Libro ciencia2 = new Libro("5", "Fundación", "Isaac Asimov", 6, 1951, "Ciencia Ficción");
        Libro ciencia3 = new Libro("6", "Neuromante", "William Gibson", 3, 1984, "Ciencia Ficción");
        Libro ciencia4 = new Libro("7", "Solaris", "Stanislaw Lem", 2, 1961, "Ciencia Ficción");

        // --- GÉNERO: FANTASÍA ---
        Libro fantasía1 = new Libro("8", "El Hobbit", "J.R.R. Tolkien", 8, 1937, "Fantasía");
        Libro fantasía2 = new Libro("9", "El nombre del viento", "Patrick Rothfuss", 5, 2007, "Fantasía");
        Libro fantasía3 = new Libro("10", "Geralt de Rivia", "Andrzej Sapkowski", 4, 1993, "Fantasía");

        ArrayList<Libro> listaLibros = new ArrayList<>();

        listaLibros.add(terror1);
        listaLibros.add(terror2);
        listaLibros.add(terror3);
        listaLibros.add(ciencia1);
        listaLibros.add(ciencia2);
        listaLibros.add(ciencia3);
        listaLibros.add(ciencia4);
        listaLibros.add(fantasía1);
        listaLibros.add(fantasía2);
        listaLibros.add(fantasía3);

        // --- CREACIÓN DE LA BIBLIOTECA ---
        
        Biblioteca miBiblioteca = new Biblioteca("miBiblioteca", listaLibros);


        //------Mostrar toda la bilioteca------
        System.out.println(miBiblioteca.toString());

        //------Mostrar libros por genero------
        System.out.println("Libros por genero");
        for (Libro l : miBiblioteca.librosPorGenero("Ciencia Ficción")) {
            System.out.println(l);
        }
             
        //------Mostrar libros por fecha------
        System.out.println("Libros posteriores a fecha");
        for (Libro l : miBiblioteca.librosPosterioresA(1920)) {
            System.out.println(l);
        }
        
       
    
    }
}

