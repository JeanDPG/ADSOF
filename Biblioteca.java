import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca{

    private String nombre;
    private HashMap<String, ArrayList<Libro>> libros;
    
    // 2 formas de contruir la bilioteca

    //Constructor sin pasarle libros. Se añaden a posteriori con una funcion
    /* 
    public Biblioteca(String nombre){
        this.nombre = nombre;
        this.libros = new HashMap<>();
    }
    */

    /**
     * Constructor que recibe una lista de libros. Se añaden al HashMap en el constructor
     * @param nombre
     * @param listaEntrada
     */
    public Biblioteca(String nombre, ArrayList<Libro> listaEntrada) {
    this.nombre = nombre;
    this.libros = new HashMap<>();
    
        for (Libro l : listaEntrada) {
        String genero = l.getGenero();
        
        //Obtener la lista para ese género
        ArrayList<Libro> listaPorGenero = this.libros.get(genero);
        
        //Si no existe el genero, creamos la lista del genero
        if (listaPorGenero == null) {
            listaPorGenero = new ArrayList<>();
            this.libros.put(genero, listaPorGenero);
        }
        
        //Añadir libro a la lista
        listaPorGenero.add(l);
    }
    }
    /**
     * Función para añadir libros a la biblioteca. Si el genero no existe, se crea una nueva lista para ese genero
     * @param libro
     */
    public void setLibroBiblioteca(Libro libro){
        if(!this.libros.containsKey(libro.getGenero())){
            this.libros.put(libro.getGenero(), new ArrayList<>());
        }
        //HashMap.get(key) devuelve el value(lista) de la key
        this.libros.get(libro.getGenero()).add(libro);
      
    }

    /**
     * Función para obtener los libros de un genero concreto. Si el genero no existe, devuelve null
     * @param genero
     * @return
     */
    public ArrayList<Libro> librosPorGenero(String genero) {
    
    // El HashMap busca directamente por la clave 'genero'
    ArrayList<Libro> librosEncontrado = this.libros.get(genero); 
    
    return (librosEncontrado == null) ? null : librosEncontrado;
    }
    
    /**
     * Función para obtener los libros posteriores a un año concreto. Si el genero no existe, devuelve null
     * @param añoPublicacion
     * @return
     */ 
    /*
    public ArrayList<Libro> librosPosterioresA(int añoPublicacion){
        ArrayList<Libro> libros = new ArrayList<>();
        this.libros.forEach((clave,valor)->{
            for (Libro libro : valor) {
                if(libro.getAño() > añoPublicacion) libros.add(libro);
            }
        });
        
        return libros;
    } 
    */

    /*Forma moderna */
    /**
     * Función para obtener los libros posteriores a un año concreto. Si el genero no existe, devuelve null
     * @param añoPublicacion
     * @return
     */
    public List<Libro> librosPosterioresA(int añoPublicacion) {
    return this.libros.values().stream()         // 1. Entramos en las listas de libros
            .flatMap(lista -> lista.stream())    // 2. Unimos todas las listas en un solo flujo
            .filter(l -> l.getAño() > añoPublicacion) // 3. El colador (filter)
            .toList();                           // 4. Convertimos a lista
    }
     
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== Inventario de la Biblioteca: " + this.nombre + " ===\n");
        
        for (Map.Entry<String, ArrayList<Libro>> entry : libros.entrySet()) {
        String genero = entry.getKey();
        ArrayList<Libro> listaLibros = entry.getValue();

        sb.append("\nGÉNERO: ").append(genero.toUpperCase()).append("\n");
        sb.append("---------------------------\n");

        // Bucle anidado para recorrer la lista de libros de este género
        for (Libro libro : listaLibros) {
            sb.append("  - ").append(libro).append("\n"); // Llama al toString de Libro
        }
    }
        
        return sb.toString();
    }

}



