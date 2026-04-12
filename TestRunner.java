import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * TestRunner - Ejecuta todos los tests sin necesidad de JUnit
 * Versión ajustada para trabajar con paquetes
 *
 * ESTRUCTURA ESPERADA:
 * src/main/java/org/example/
 * ├── estrategias/
 * │   ├── Estrategia1.java
 * │   ├── Estrategia2.java
 * │   ├── Estrategia3.java
 * │   └── EstrategiaSimulacion.java
 * ├── TodasLasEstrategiasTest.java
 * └── TestRunner.java
 */
public class TestRunner {

    private static int totalTests = 0;
    private static int testsExitosos = 0;
    private static int testsFallidos = 0;
    private static List<String> fallos = new ArrayList<>();

    /** Arranca la ejecución manual de la suite de tests. */
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 EJECUTOR DE TESTS - Sistema de Estrategias");
        System.out.println("=".repeat(70) + "\n");

        ejecutarTests();

        mostrarResumen();
    }

    /** Localiza los tests disponibles y los ejecuta uno a uno. */
    private static void ejecutarTests() {
        try {
            Class<?> testClass = Class.forName("TodasLasEstrategiasTest");
            Method[] metodos = testClass.getDeclaredMethods();

            List<Method> testsAEjecutar = new ArrayList<>();
            for (Method metodo : metodos) {
                if (metodo.getName().startsWith("test")) {
                    testsAEjecutar.add(metodo);
                }
            }

            System.out.println("📊 Tests encontrados: " + testsAEjecutar.size());
            System.out.println("-".repeat(70) + "\n");

            Method setUpMethod = null;
            try {
                setUpMethod = testClass.getDeclaredMethod("setUp");
            } catch (NoSuchMethodException e) {
            }

            for (Method testMethod : testsAEjecutar) {
                ejecutarTest(testClass, testMethod, setUpMethod);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ ERROR: No se encontró la clase TodasLasEstrategiasTest");
            System.out.println("   Asegúrate de:");
            System.out.println("   1. Compilar correctamente:");
            System.out.println("      javac -d . estrategias/*.java TodasLasEstrategiasTest.java TestRunner.java");
            System.out.println("   2. Estar en la carpeta correcta: src/main/java/org/example/");
            System.out.println("   3. Ejecutar desde la carpeta padre:");
            System.out.println("      java -cp . org.example.TestRunner");
        }
    }

    /** Ejecuta un test concreto e informa del resultado en consola. */
    private static void ejecutarTest(Class<?> testClass, Method testMethod, Method setUpMethod) {
        totalTests++;
        String nombreTest = testMethod.getName();

        try {
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            if (setUpMethod != null) {
                setUpMethod.setAccessible(true);
                setUpMethod.invoke(testInstance);
            }

            testMethod.setAccessible(true);
            testMethod.invoke(testInstance);

            testsExitosos++;
            System.out.println("✅ " + nombreTest);

        } catch (AssertionError e) {
            testsFallidos++;
            String mensaje = e.getMessage() != null ? e.getMessage() : e.toString();
            fallos.add(nombreTest + ": " + mensaje);
            System.out.println("❌ " + nombreTest);
            System.out.println("   └─ " + mensaje);

        } catch (Exception e) {
            testsFallidos++;
            String mensaje = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            fallos.add(nombreTest + ": " + mensaje);
            System.out.println("❌ " + nombreTest);
            System.out.println("   └─ ERROR: " + mensaje);
        }
    }

    /** Muestra por consola el resumen final de la ejecución. */
    private static void mostrarResumen() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("📈 RESUMEN DE EJECUCIÓN");
        System.out.println("=".repeat(70));

        System.out.println("\n📊 Estadísticas:");
        System.out.println("   Total de tests:    " + totalTests);
        System.out.println("   ✅ Exitosos:       " + testsExitosos);
        System.out.println("   ❌ Fallidos:       " + testsFallidos);

        double porcentaje = totalTests > 0 ? (testsExitosos * 100.0) / totalTests : 0;
        System.out.printf("   📈 Éxito:          %.1f%%\n", porcentaje);

        System.out.println("   ⏱️  Tiempo:         ~1-2 segundos");

        if (!fallos.isEmpty()) {
            System.out.println("\n❌ DETALLES DE FALLOS:");
            for (String fallo : fallos) {
                System.out.println("   • " + fallo);
            }
        }

        System.out.println("\n" + "-".repeat(70));
        if (testsFallidos == 0) {
            System.out.println("🎉 ¡TODOS LOS TESTS PASARON CORRECTAMENTE! 🎉");
        } else {
            System.out.println("⚠️  " + testsFallidos + " test(s) fallido(s)");
        }
        System.out.println("=".repeat(70) + "\n");
    }
}
