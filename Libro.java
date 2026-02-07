public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int ejemplaresDisponibles;
    private int año;
    private String genero;

    public Libro(String isbn, String titulo, String autor, int ejemplaresDisponibles, int año, String genero){
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.año = año;
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }
    
    public int getAño(){
        return this.año;
    }

    public boolean estaDisponible(){
        return this.ejemplaresDisponibles > 0;
    }

    public boolean prestar(){
        if(estaDisponible()){
            this.ejemplaresDisponibles--;
            return true;
        }
        return false;
    }

    public void devolver(){
        this.ejemplaresDisponibles++;
    }

    private String description(){
        String estado = this.estaDisponible() ? "Disponible" : "No disponible";
        return "'" + this.titulo + "' de " + this.año + " de " + this.autor + ".Genero: " +  this.genero + " [" + estado + "]";
    }

    

    
    @Override
    public String toString() {
        return "ISBN: " + this.isbn + ". " + this.description() + " (" + this.ejemplaresDisponibles
         + " ejemplares disponibles)";
    }
         
        


}
