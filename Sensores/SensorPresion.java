package Sensores;

import Procesadores.Procesador;
import Sensores.Estrategias.Estrategia1;
import Sensores.Estrategias.EstrategiaSimulacion;

public class SensorPresion extends Sensor {
    private static int contador = 1;
    private static final double PROB_DEFECTO = 0.05;
    private static final UnidadDeMedida UNIDAD_DEFECTO = UnidadDeMedida.HECTOPASCAL;


    // Constructor 1: Solo parámetros obligatorios
    public SensorPresion(double offset, long horasCaducidad) {
        this(offset, horasCaducidad, UNIDAD_DEFECTO, new Estrategia1(minimoPorUnidad(UNIDAD_DEFECTO), maximoPorUnidad(UNIDAD_DEFECTO), PROB_DEFECTO));
    }

    // Constructor 2: Con unidad personalizada
    public SensorPresion(double offset, long horasCaducidad, UnidadDeMedida unidad) {
        this(offset, horasCaducidad, unidad,
                new Estrategia1(minimoPorUnidad(unidad), maximoPorUnidad(unidad), PROB_DEFECTO));
    }

    // Constructor 3: Con estrategia personalizada
    public SensorPresion(double offset, long horasCaducidad, EstrategiaSimulacion estrategia) {
        this(offset, horasCaducidad, UNIDAD_DEFECTO, estrategia);
    }

    // Constructor 4: Parámetros completos (DELEGA A SUPER)
    public SensorPresion(double offset, long horasCaducidad,
                         UnidadDeMedida unidad, EstrategiaSimulacion estrategia) {
        this(offset, horasCaducidad, unidad, estrategia, new Procesador());
    }

    public SensorPresion(double offset, long horasCaducidad,
                         UnidadDeMedida unidad, EstrategiaSimulacion estrategia, Procesador procesador) {
        super(TipoSensor.PRESION,
                "PRES-" + String.format("%04d", contador++),
                unidad,
                offset,
                horasCaducidad,
                estrategia,
                procesador);
    }

    @Override
    public boolean validarRango(double valor) {
        double min = minimoPorUnidad(getUnidadDeLectura());
        double max = maximoPorUnidad(getUnidadDeLectura());
        return valor >= min && valor <= max;
    }

    private static double minimoPorUnidad(UnidadDeMedida unidad) {
        switch (unidad) {
            case HECTOPASCAL:
            case MILIBAR:
                return 300.0;
            case PASCAL:
                return 30000.0;
            default:
                return 300.0;
        }
    }

    private static double maximoPorUnidad(UnidadDeMedida unidad) {
        switch (unidad) {
            case HECTOPASCAL:
            case MILIBAR:
                return 1100.0;
            case PASCAL:
                return 110000.0;
            default:
                return 1100.0;
        }
    }
}
