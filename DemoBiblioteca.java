
    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    
public class DemoBiblioteca {
    public static void main(String[] args) {


                // --- GÉNERO: TERROR ---
        Libro terror1 = new Libro("978-01", "It", "Stephen King", 5, 1986, "Terror");
        Libro terror2 = new Libro("978-02", "Drácula", "Bram Stoker", 3, 1897, "Terror");
        Libro terror3 = new Libro("978-03", "El resplandor", "Stephen King", 4, 1977, "Terror");

        // --- GÉNERO: CIENCIA FICCIÓN ---
        Libro ciencia1 = new Libro("978-04", "Dune", "Frank Herbert", 2, 1965, "Ciencia Ficción");
        Libro ciencia2 = new Libro("978-05", "Fundación", "Isaac Asimov", 6, 1951, "Ciencia Ficción");
        Libro ciencia3 = new Libro("978-06", "Neuromante", "William Gibson", 3, 1984, "Ciencia Ficción");
        Libro ciencia4 = new Libro("978-07", "Solaris", "Stanislaw Lem", 2, 1961, "Ciencia Ficción");

        // --- GÉNERO: FANTASÍA ---
        Libro fantasía1 = new Libro("978-08", "El Hobbit", "J.R.R. Tolkien", 8, 1937, "Fantasía");
        Libro fantasía2 = new Libro("978-09", "El nombre del viento", "Patrick Rothfuss", 5, 2007, "Fantasía");
        Libro fantasía3 = new Libro("978-10", "Geralt de Rivia", "Andrzej Sapkowski", 4, 1993, "Fantasía");

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

         /* Con el constructor vacio 
        Biblioteca miBiblioteca = new Biblioteca("Otra biblioteca");
        otraBiblioteca.setLibroBiblioteca(terror1);
        otraBiblioteca.setLibroBiblioteca(terror2);
        otraBiblioteca.setLibroBiblioteca(terror3);
        otraBiblioteca.setLibroBiblioteca(ciencia1);
        otraBiblioteca.setLibroBiblioteca(ciencia2);
        otraBiblioteca.setLibroBiblioteca(ciencia3);
        otraBiblioteca.setLibroBiblioteca(ciencia4);
        otraBiblioteca.setLibroBiblioteca(fantasía1);
        otraBiblioteca.setLibroBiblioteca(fantasía2);
        otraBiblioteca.setLibroBiblioteca(fantasía3);
        */

        //------Mostrar toda la bilioteca------
        System.out.println(miBiblioteca.toString());

        //------Mostrar libros por genero------
        System.out.println("Libros por genero");
        for (Libro l : miBiblioteca.librosPorGenero("Ciencia Ficción")) {
            System.out.println(l);
        }
             
        //------Mostrar libros por genero------
        System.out.println("Libros posteriores a fecha");
        for (Libro l : miBiblioteca.librosPosterioresA(1920)) {
            System.out.println(l);
        }
        
       
    
    }
}

