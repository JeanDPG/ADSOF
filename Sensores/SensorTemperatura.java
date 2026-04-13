package Sensores;

import Sensores.Estrategias.Estrategia1;
import Sensores.Estrategias.EstrategiaSimulacion;

public class SensorTemperatura extends Sensor {
    private static int contador = 1;
    private static final double PROB_DEFECTO = 0.05;
    private static final UnidadDeMedida UNIDAD_DEFECTO = UnidadDeMedida.CELSIUS;



    // Constructor 1: Solo parámetros obligatorios
    public SensorTemperatura(double offset, long horasCaducidad) {
        this(offset, horasCaducidad, UNIDAD_DEFECTO,
                new Estrategia1(minimoPorUnidad(UNIDAD_DEFECTO), maximoPorUnidad(UNIDAD_DEFECTO), PROB_DEFECTO));
    }

    // Constructor 2: Con unidad personalizada
    public SensorTemperatura(double offset, long horasCaducidad, UnidadDeMedida unidad) {
        this(offset, horasCaducidad, unidad,
                new Estrategia1(minimoPorUnidad(unidad), maximoPorUnidad(unidad), PROB_DEFECTO));
    }

    // Constructor 3: Con estrategia personalizada
    public SensorTemperatura(double offset, long horasCaducidad, EstrategiaSimulacion estrategia) {
        this(offset, horasCaducidad, UNIDAD_DEFECTO, estrategia);
    }

    // Constructor 4: Parámetros completos
    public SensorTemperatura(double offset, long horasCaducidad,
                             UnidadDeMedida unidad, EstrategiaSimulacion estrategia) {
        super(TipoSensor.TEMPERATURA,
                "TEMP-" + String.format("%04d", contador++), unidad, offset, horasCaducidad, estrategia);
    }

    
}
