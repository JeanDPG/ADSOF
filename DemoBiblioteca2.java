import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
Clase de demostración de la clase Biblioteca. Permite al usuario interactuar con la biblioteca a través de un menú en consola. 
El usuario puede consultar el inventario completo, buscar libros por género o ver libros posteriores a una fecha concreta.
*/

public class DemoBiblioteca2 {
    

    public static void main(String[] args) throws IOException{

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
        
        Biblioteca miBiblioteca = new Biblioteca("Biblioteca Central", listaLibros);

        int inputNum = 0;
        while(inputNum != 4){
        do{
            System.out.println("\nMenu Biblioteca");
            System.out.println("-------------------------------");
            System.out.println(" 1- Consultar Biblioteca \n 2- Consultar libros por genero \n 3- Consultar libros posteiores a una fecha \n 4- Salir");
            String input = reader.readLine();
            inputNum = Integer.parseInt(input);
        }while(inputNum != 1 && inputNum != 2 && inputNum != 3 && inputNum != 4);
    
        switch (inputNum) {
            case 1:
                System.out.println("--- MOSTRAR INVENTARIO COMPLETO ---");
                System.out.println(miBiblioteca.toString());
                break;
                

            case 2:
                System.out.println("--- BUSCAR LIBROS POR GÉNERO ---");
                System.out.print("Introduce el genero: ");
                String genero = reader.readLine();
               
                for (Libro libro : miBiblioteca.librosPorGenero(genero)) {
                    System.out.println(libro);
                }
                break;

            case 3:
                System.out.println("--- VER LIBROS POSTERIORES A UN AÑO ---");
                System.out.print("Introduce el año: ");
                String year = reader.readLine();
                int inputYear = Integer.parseInt(year);
                System.out.println("Libros posteriores a " + inputYear);
                for (Libro libro : miBiblioteca.librosPosterioresA(inputYear)) {
                    System.out.println(libro);
                }
                break;
                
            case 4:
                System.out.println("¡Adiós!");
                System.exit(0);
                break;

            default:
                
                break;
        }

        
        
        
    }
    }
}
