package redSocial;

/**
 * Programa de pruebas extensas para validar la lógica de las clases Enlace, Usuario y Mensaje.
 * Verifica validaciones de costes, duplicados de enlaces y lógica de difusión múltiple con fallos.
 * 
 * @author Jaime Garcia, Jean del Pozo
 * @version 1.1
 */
public class PruebasCompletasRedSocial {

    /**
     * Ejecuta una serie de pruebas para comprobar el funcionamiento del sistema.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        
        System.out.println("--- TEST APARTADO 1: ENLACE ---");
        Usuario u1 = new Usuario("Test1");
        Usuario u2 = new Usuario("Test2");
        
        // Comprobar coste <= 0, debe ser automaticamente 1
        Enlace e1 = new Enlace(u1, u2, -5);
        System.out.println("Coste negativo corregido a 1: " + (e1.getCoste() == 1));
        
        // Comprobar constructor sin coste (debe ser 1 por defecto)
        Enlace eSinCoste = new Enlace(u1, u2);
        System.out.println("Coste por defecto es 1: " + (eSinCoste.getCoste() == 1));
        
        // Comprobar suma estática de costes
        int sumaInicial = Enlace.getSumaDeCostes();
        Enlace e2 = new Enlace(u1, u2, 10);
        System.out.println("Suma estática incrementada: " + (Enlace.getSumaDeCostes() == sumaInicial + 10));
        
        // Comprobar cambiarDestino y actualización de suma estática
        e2.cambiarDestino(u2, 20);
        System.out.println("Suma estática tras cambiarDestino: " + Enlace.getSumaDeCostes());

        System.out.println("\n--- TEST APARTADO 2: USUARIO ---");
        Usuario ana = new Usuario("ana", 5);
        Usuario luis = new Usuario("luis", 10);
        
        // Probar constructor por defecto (capacidad debe ser 2)
        Usuario defecto = new Usuario("defecto");
        System.out.println("Capacidad por defecto es 2: " + (defecto.getCapacidadAmp() == 2));
        
        // Probar addEnlace: enlace con mismo usuario inicial y final (debe fallar)
        boolean autoRef = ana.addEnlace(new Enlace(ana, ana, 10));
        System.out.println("Añadir autorreferencia falla: " + (!autoRef));
        
        // Probar addEnlace: origen incorrecto (debe fallar)
        boolean origenInc = ana.addEnlace(new Enlace(luis, ana, 5));
        System.out.println("Añadir enlace con origen distinto falla: " + (!origenInc));
        
        // Probar addEnlace: duplicado al mismo destino (debe fallar)
        ana.addEnlace(luis, 15);
        boolean duplicado = ana.addEnlace(luis, 20);
        System.out.println("Añadir enlace a destino duplicado falla: " + (!duplicado));
        
        // Probar getters de enlaces
        System.out.println("Número de enlaces de ana (debe ser 1): " + ana.getNumEnlaces());
        System.out.println("Get enlace por índice 0: " + ana.getEnlace(0));
        System.out.println("Get enlace por Usuario luis: " + ana.getEnlace(luis));

        System.out.println("\n--- TEST APARTADO 3: MENSAJE ---");
        Usuario carmen = new Usuario("carmen", 3);
        Usuario pablo = new Usuario("pablo", 2);
        
        // Enlaces: ana(5) --15--> luis(10) --50--> carmen(3) --5--> pablo(2)
        luis.addEnlace(carmen, 50);
        carmen.addEnlace(pablo, 5);
        
        Mensaje msg = new Mensaje("Secreto", 60, ana);
        System.out.println("Estado inicial: " + msg);
        
        // Difusión simple (ana -> luis)
        msg.difunde(ana.getEnlace(luis));
        System.out.println("Tras ana->luis: " + msg);
        
        // Difusión múltiple (estamos en luis, intentamos ir a carmen y luego a pablo)
        boolean exitoMultiple = msg.difunde(carmen, pablo);
        System.out.println("¿Difusión múltiple exitosa?: " + exitoMultiple);
        System.out.println("Estado final tras cadena: " + msg);
        
        // Probar fallo por alcance insuficiente
        pablo.addEnlace(ana, 100);
        boolean falloAlcance = msg.difunde(pablo.getEnlace(ana));
        System.out.println("Fallo esperado por alcance insuficiente: " + (!falloAlcance));
        System.out.println("El mensaje se quedó en: " + msg.getUsuarioActual().getNombre());

        System.out.println("\n--- TEST APARTADO 4: SALTOS CON FALLOS EN DIFUSIÓN ---");
        /* 
         * Prueba del requisito: "si no existe el enlace o el alcance no es suficiente, 
         * se intenta directamente con el siguiente usuario de la lista, sin detener la difusión".
         */
        Usuario fede = new Usuario("fede");
        // Escenario: ana -> luis (existe), luis -> fede (NO existe), luis -> pablo (creamos enlace ahora)
        luis.addEnlace(pablo, 10);
        
        Mensaje msgSalto = new Mensaje("Salto", 100, ana);
        
        // Intentamos: ana -> luis (OK), luis -> fede (FALLO, saltar), luis -> pablo (OK)
        // El resultado debe ser FALSE porque hubo un fallo en 'fede', pero debe acabar en 'pablo'
        boolean resultadoGlobal = msgSalto.difunde(luis, fede, pablo);
        
        System.out.println("¿El retorno es FALSE por el fallo de fede?: " + (!resultadoGlobal));
        System.out.println("¿Ubicación final es @pablo?: " + msgSalto.getUsuarioActual().getNombre().equals("pablo"));
        System.out.println("Estado final tras saltos: " + msgSalto);
        
        System.out.println("\n--- TEST TOSTRING FORMATOS ---");
        System.out.println("Formato Enlace: " + e2);
        System.out.println("Formato Usuario: " + ana);
        System.out.println("Formato Mensaje: " + msgSalto);
    }
}