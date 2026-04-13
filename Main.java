import Procesadores.Conversores.Conversor;
import Procesadores.Conversores.ConversorIdentidad;
import Procesadores.Conversores.Presion.ConversorHectoPascalPascal;
import Procesadores.Conversores.Presion.ConversorMilibarHectoPascal;
import Procesadores.Conversores.Presion.ConversorPascalMilibar;
import Procesadores.Conversores.Temperatura.ConversorCelsiusKelvin;
import Procesadores.Conversores.Temperatura.ConversorFahrenheitCelsius;
import Procesadores.Conversores.Temperatura.ConversorKelvinFahrenheit;
import Procesadores.Conversores.UnidadIncompatibleException;
import Procesadores.Procesador;
import Sensores.Estrategias.Estrategia1;
import Sensores.Estrategias.Estrategia2;
import Sensores.Estrategias.Estrategia3;
import Sensores.Estrategias.EstrategiaSimulacion;
import Sensores.Sensor;
import Sensores.SensorDuplicadoException;
import Sensores.SensorHumedad;
import Sensores.SensorPresion;
import Sensores.SensorTemperatura;
import Sensores.TipoSensor;
import Sensores.UnidadDeMedida;
import documentos.IDocumento;
import estacion.EstacionMeteorologica;
import estacion.EstacionMeteorologicaDocumento;
import formateadores.HtmlDocumentFormatter;
import formateadores.MarkdownDocumentFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        imprimirSeccion("ESTRATEGIAS");
        probarEstrategias();

        imprimirSeccion("SENSORES");
        probarSensores();

        imprimirSeccion("ESTACION METEOROLOGICA");
        probarEstacion();

        imprimirSeccion("CONVERSORES");
        probarConversores();

        imprimirSeccion("PROCESADOR");
        probarProcesador();

        imprimirSeccion("FORMATEADORES");
        probarFormateadores();
    }

    private static void probarFormateadores() {
        // 1. Create an instance of EstacionMeteorologica
        EstacionMeteorologica estacion = new EstacionMeteorologica("Madrid Centro", 40.4168, -3.7038);

        try {
            // 2. Add a couple of sensors with some readings using the correct constructors
            // Sensor 1 with Kelvin conversion
            Sensor s1 = new SensorTemperatura(0, 24, UnidadDeMedida.CELSIUS);
            Procesador p1 = new Procesador();
            p1.setConversor(new ConversorCelsiusKelvin());
            s1.setProcesador(p1);
            s1.tomarLectura(20.74); // -> 293.89 K
            s1.tomarLectura(20.57); // -> 293.72 K
            s1.tomarLectura(20.19); // -> 293.34 K
            estacion.añadirSensor(s1);

            // Sensor 2 without conversion
            Sensor s2 = new SensorTemperatura(0, 24, UnidadDeMedida.CELSIUS);
            s2.tomarLectura(20.02);
            s2.tomarLectura(20.88);
            s2.tomarLectura(20.33);
            estacion.añadirSensor(s2);

        } catch (SensorDuplicadoException e) {
            System.out.println("Error al añadir sensor: " + e.getMessage());
        }

        // 3. Adapt it to the IDocumento interface
        IDocumento documento = new EstacionMeteorologicaDocumento(estacion);

        // 4. Create instances of the formatters
        HtmlDocumentFormatter htmlFormatter = new HtmlDocumentFormatter();
        MarkdownDocumentFormatter markdownFormatter = new MarkdownDocumentFormatter();

        // 5. Generate the string outputs
        String htmlOutput = htmlFormatter.format(documento);
        String markdownOutput = markdownFormatter.format(documento);

        // 6. Print the results to the console
        System.out.println("\n--- SALIDA HTML ---\n");
        System.out.println(htmlOutput);
        System.out.println("\n--- SALIDA MARKDOWN ---\n");
        System.out.println(markdownOutput);

        // 7. Write the output to files
        try {
            Files.writeString(Paths.get("output.html"), htmlOutput);
            System.out.println("\nArchivo 'output.html' generado con éxito en la raíz del proyecto.");
            Files.writeString(Paths.get("output.md"), markdownOutput);
            System.out.println("Archivo 'output.md' generado con éxito en la raíz del proyecto.");
        } catch (IOException e) {
            System.out.println("\nError al escribir los ficheros de salida: " + e.getMessage());
        }
    }

    private static void probarEstrategias() {
        EstrategiaSimulacion estrategia1 = new Estrategia1(-10.0, 40.0, 0.05);
        EstrategiaSimulacion estrategia2 = new Estrategia2(22.0, 0.10);
        EstrategiaSimulacion estrategia3 = new Estrategia3(Arrays.asList(18.0, 20.0, 22.0, 24.0), 0.15);

        imprimirValorEstrategia("Estrategia1", estrategia1);
        imprimirValorEstrategia("Estrategia2", estrategia2);
        imprimirValorEstrategia("Estrategia3", estrategia3);
    }

    private static void probarSensores() {
        EstrategiaSimulacion estrategiaTemp = new Estrategia2(21.0, 0.08);
        EstrategiaSimulacion estrategiaPres = new Estrategia3(Arrays.asList(1008.0, 1012.0, 1015.0), 0.03);

        List<Sensor> sensores = Arrays.asList(
                new SensorTemperatura(0.0, 24),
                new SensorTemperatura(0.5, 24, UnidadDeMedida.FAHRENHEIT),
                new SensorTemperatura(-0.2, 24, estrategiaTemp),
                new SensorTemperatura(0.1, 24, UnidadDeMedida.KELVIN, new Estrategia1(0.0, 1273.15, 0.02)),
                new SensorPresion(0.0, 48),
                new SensorPresion(1.0, 48, UnidadDeMedida.PASCAL),
                new SensorPresion(-0.5, 48, estrategiaPres),
                new SensorPresion(0.2, 48, UnidadDeMedida.MILIBAR, new Estrategia1(300.0, 1100.0, 0.01)),
                new SensorHumedad(0.0, 12)
        );

        double[] lecturas = {23.4, 72.0, 20.5, 298.15, 1013.0, 101325.0, 1010.5, 1008.8, 54.2};

        for (int i = 0; i < sensores.size(); i++) {
            Sensor sensor = sensores.get(i);
            sensor.tomarLectura(lecturas[i]);
            System.out.println(sensor);
            System.out.println("  tipo=" + sensor.getTipo()
                    + ", unidad=" + sensor.getUnidadDeLectura()
                    + ", calibrado=" + sensor.estaCorrectamenteCalibrado());
        }
    }

    private static void probarEstacion() {
        EstacionMeteorologica estacion = new EstacionMeteorologica("Madrid Norte", 40.45, -3.69);
        Sensor temperatura = new SensorTemperatura(0.0, 24);
        Sensor humedad = new SensorHumedad(0.0, 12);
        Sensor presion = new SensorPresion(0.0, 48, UnidadDeMedida.HECTOPASCAL);

        try {
            estacion.añadirSensor(temperatura);
            estacion.añadirSensor(humedad);
            estacion.añadirSensor(presion);
            estacion.añadirSensor(temperatura);
        } catch (SensorDuplicadoException e) {
            System.out.println("Duplicado detectado: " + e.getMessage());
        }

        System.out.println(estacion);
        System.out.println("Sensores registrados: " + estacion.getSensoresRegistrados().size());
        System.out.println("Buscar por id: " + estacion.recuperarSensorPorId(temperatura.getId()));
        System.out.println("Filtrar temperatura: " + estacion.obtenerSensoresPorTipo(TipoSensor.TEMPERATURA).size());
        System.out.println("Filtrar humedad: " + estacion.obtenerSensoresPorTipo(TipoSensor.HUMEDAD).size());
        System.out.println("Filtrar presion: " + estacion.obtenerSensoresPorTipo(TipoSensor.PRESION).size());
    }

    private static void probarConversores() {
        Conversor identidad = new ConversorIdentidad("mbar");
        Conversor fAC = new ConversorFahrenheitCelsius();
        Conversor cAK = new ConversorCelsiusKelvin();
        Conversor kAF = new ConversorKelvinFahrenheit();
        Conversor mbarAHpa = new ConversorMilibarHectoPascal();
        Conversor hpaAPa = new ConversorHectoPascalPascal();
        Conversor paAMbar = new ConversorPascalMilibar();

        System.out.println("Identidad 15.5 -> " + identidad.convertir(15.5) + " " + identidad.getUnidadDestino());
        System.out.println("32 F -> " + fAC.convertir(32.0) + " " + fAC.getUnidadDestino());
        System.out.println("0 C -> " + cAK.convertir(0.0) + " " + cAK.getUnidadDestino());
        System.out.println("273.15 K -> " + kAF.convertir(273.15) + " " + kAF.getUnidadDestino());
        System.out.println("1013.25 mbar -> " + mbarAHpa.convertir(1013.25) + " " + mbarAHpa.getUnidadDestino());
        System.out.println("1013.25 hPa -> " + hpaAPa.convertir(1013.25) + " " + hpaAPa.getUnidadDestino());
        System.out.println("101325 Pa -> " + paAMbar.convertir(101325.0) + " " + paAMbar.getUnidadDestino());

        try {
            Conversor compuestoTemp = fAC.concatenarCon(cAK);
            System.out.println("32 F -> " + compuestoTemp.convertir(32.0) + " " + compuestoTemp.getUnidadDestino());

            //Conversor compuestoPresion = new ConversorCompuesto(mbarAHpa, hpaAPa);
           // System.out.println("1000 mbar -> " + compuestoPresion.convertir(1000.0) + " " + compuestoPresion.getUnidadDestino());

            fAC.concatenarCon(paAMbar);
        } catch (UnidadIncompatibleException e) {
            System.out.println("Concatenacion incompatible detectada");
        }
    }

    private static void probarProcesador() {
        Procesador procesador = new Procesador();
        procesador.setValorHistorico(new Date(1_000L), 10.0);
        procesador.setValorHistorico(new Date(2_000L), 20.0);
        procesador.setValorHistorico(new Date(3_000L), 15.0);
        System.out.println("Procesador sin conversor especifico:" + procesador);

        Procesador procesador2 = new Procesador();
        procesador2.setConversor(new ConversorCelsiusKelvin());
        procesador2.setValorHistorico(new Date(1_000L), 10.0);
        procesador2.setValorHistorico(new Date(2_000L), 20.0);
        procesador2.setValorHistorico(new Date(3_000L), 15.0);
        System.out.println("Procesador con conversor cambiado:" + procesador2);
    }

    private static void imprimirSeccion(String titulo) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println(titulo);
        System.out.println("=".repeat(60));
    }

    private static void imprimirValorEstrategia(String nombre, EstrategiaSimulacion estrategia) {
        System.out.println(nombre + " -> " + estrategia.generarValorAleat());
    }
}
