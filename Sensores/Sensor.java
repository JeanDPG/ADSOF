package Sensores;

import Excepciones.*;
import Sensores.Estrategias.EstrategiaSimulacion;

import java.time.*;

public abstract class Sensor {
    private String id;
    private double offset;
    private TipoSensor tipo;
    private UnidadDeMedida unidadDeLectura;
    private LocalDateTime fechaUltimaLectura;
    private Double valorUltimaLectura;
    private LocalDate fechaDeInstalacion;
    private LocalDateTime fechaUltimaCalibracion;
    private LocalDateTime fechaCaducidadCalibracion;
    private long duracionCalibracionDias;
    private boolean calibradoPorRango;
    private Double valorLecturaAnterior;
    private double porcentajeCambioBrusco;
    private boolean medicionDetenida;
    private EstrategiaSimulacion estrategia;

    // Constructor base (privado) - inicializa todos los campos
    private Sensor(TipoSensor tipo, String id, double offset, long duracionCalibracion,
                   UnidadDeMedida unidad, EstrategiaSimulacion estrategia) {
        this.tipo = tipo;
        this.id = id;
        this.offset = offset;
        this.fechaUltimaCalibracion = LocalDateTime.now();
        this.duracionCalibracionDias = duracionCalibracion;
        this.fechaCaducidadCalibracion = this.fechaUltimaCalibracion.plusDays(this.duracionCalibracionDias);
        this.unidadDeLectura = unidad;
        this.estrategia = estrategia;
        this.fechaDeInstalacion = LocalDate.now();
        
        this.calibradoPorRango = true;
        this.medicionDetenida= false;
        this.porcentajeCambioBrusco = 0.5;
    }

    // Constructor A: El más básico (Tipo, ID, Offset). Pone 365 días por defecto.
    public Sensor(TipoSensor tipo, String id, double offset) {
        this(tipo, id, offset, 365, null, null);
    }

    // Constructor B: Con duración específica de calibración (en días)
    public Sensor(TipoSensor tipo, String id, double offset, long duracionDias) {
        this(tipo, id, offset, duracionDias, null, null);
    }

    // Constructor C: Con estrategia de simulación (usa 365 días por defecto)
    public Sensor(TipoSensor tipo, String id, double offset, EstrategiaSimulacion estrategia) {
        this(tipo, id, offset, 365, null, estrategia);
    }

    // Constructor D: Con estrategia y duración específica
    public Sensor(TipoSensor tipo, String id, double offset, long duracionDias, EstrategiaSimulacion estrategia) {
        this(tipo, id, offset, duracionDias, null, estrategia);
    }

    // Constructor E: Con unidad de medida (usa 365 días por defecto)
    public Sensor(TipoSensor tipo, String id, UnidadDeMedida unidad, double offset) {
        this(tipo, id, offset, 365, unidad, null);
    }

    // Constructor F: Con unidad de medida y duración específica
    public Sensor(TipoSensor tipo, String id, UnidadDeMedida unidad, double offset, long duracionDias) {
        this(tipo, id, offset, duracionDias, unidad, null);
    }

    // Constructor G: El completo (Unidad, Offset, Duración y Estrategia)
    public Sensor(TipoSensor tipo, String id, UnidadDeMedida unidad, double offset, 
                  long duracionDias, EstrategiaSimulacion estrategia) {
        this(tipo, id, offset, duracionDias, unidad, estrategia);
    }

    public static double minimoPorUnidad(UnidadDeMedida unidad) {
        return unidad.getMin();
    }

    public static double maximoPorUnidad(UnidadDeMedida unidad) {
        return unidad.getMax();
    }
    public boolean validarRango(double valor){
        return valor >= this.unidadDeLectura.getMin() && valor <= this.unidadDeLectura.getMax();
    };

    /**
     * Toma una lectura del sensor y la procesa.
     * El valor tomado se resta al offset del sensor y se
     * valida si se encuentra dentro del rango permitido.
     * Si no se encuentra dentro del rango, se marca como
     * no calibrado.
     * @param valorMedido el valor medido por el sensor
     */
public double realizarLectura() throws CambioBruscoException, LecturaFueraDeRangoException, SensorSinCalibrarException {
    if (this.medicionDetenida || !this.estaCorrectamenteCalibrado()) {
            this.medicionDetenida = true; // Nos aseguramos de que se bloquee
            throw new SensorSinCalibrarException(this, this.fechaCaducidadCalibracion);
        }

    double valorSimulado = this.estrategia.generarValorAleat();

    double valorFinal = valorSimulado - this.offset;

    if (valorFinal < this.unidadDeLectura.getMin() || valorFinal > this.unidadDeLectura.getMax()) {
        this.calibradoPorRango = false;
        this.medicionDetenida = true; 
        throw new LecturaFueraDeRangoException(this, valorFinal);
    }

    // 5. COMPROBAR CAMBIO BRUSCO (Apartado 4)
    if (this.valorLecturaAnterior != null) {
        // Calculamos el porcentaje real para pasárselo a tu excepción
        double diferencia = Math.abs(valorFinal - this.valorLecturaAnterior);
        double porcentajeReal = diferencia / Math.abs(this.valorLecturaAnterior);

        if (porcentajeReal > this.porcentajeCambioBrusco) {
            double anterior = this.valorLecturaAnterior;
            // Actualizamos el estado antes de lanzar para "permitir seguir midiendo"
            actualizarEstadoLectura(valorFinal); 
            
            // Tu constructor pide: (Sensor sensor, double anterior, double actual, double porcentaje)
            throw new CambioBruscoException(this, anterior, valorFinal, porcentajeReal);
        }
    }

    // 6. ACTUALIZAR ESTADO FINAL (Si todo ha ido bien)
    actualizarEstadoLectura(valorFinal);
    return valorFinal;
}


// Método auxiliar para no repetir código
private void actualizarEstadoLectura(double valor) {
    this.valorLecturaAnterior = valor;
    this.valorUltimaLectura = valor;
    this.fechaUltimaLectura = LocalDateTime.now();
}

    /**
     * Comprueba si el sensor está  correctamente calibrado. Un sensor
     * está  correctamente calibrado si ha sido calibrado al menos una vez
     * en el rango de tiempo permitido desde la  última calibración.
     *
     * @return true si el sensor est  correctamente calibrado, false en caso contrario.
     */
    public boolean estaCorrectamenteCalibrado() {
    if (!this.calibradoPorRango) {
        return false;
    }
    return LocalDateTime.now().isBefore(this.fechaCaducidadCalibracion);
}

    public TipoSensor getTipo() { return tipo; }

    public String getId() { return id; }

    public UnidadDeMedida getUnidadDeLectura() { return unidadDeLectura; }

    protected EstrategiaSimulacion getEstrategia() {
        return estrategia;
    }

    protected void setEstrategia(EstrategiaSimulacion estrategia) {
        this.estrategia = estrategia;
    }

    public void calibrar(double nuevoOffset) {
    this.offset = nuevoOffset;
    
    this.fechaUltimaCalibracion = LocalDateTime.now();
    
    this.fechaCaducidadCalibracion = this.fechaUltimaCalibracion.plusDays(this.duracionCalibracionDias);
    
    this.calibradoPorRango = true; 
    this.medicionDetenida = false; 
    }


    public void calibrar(double nuevoOffset, long nuevosDiasDuracion) {
        this.duracionCalibracionDias = nuevosDiasDuracion;
        this.calibrar(nuevoOffset);
    }
    
    public boolean estaCalibrado() {
        boolean noHaCaducado = LocalDateTime.now().isBefore(this.fechaCaducidadCalibracion);
        return noHaCaducado && this.calibradoPorRango;
    }
    
    public void setMedicionDetenida(boolean detener) {
        this.medicionDetenida = detener;
    }


    /**
     * Devuelve una representaci n en cadena del sensor.
     * El formato es el siguiente: [TEMP-0001 (desde: 2023-09-01): Sensores.Sensor Temperatura (20.5ºC) ...]
     * Se incluye la fecha de instalaci n, el tipo de sensor, el valor
     * de la  ltima lectura y la unidad de medida de ese valor.
     * Si no se ha tomado ninguna lectura, se muestra "SIN LECTURAS" en
     * lugar del valor y la fecha de la  ltima lectura.
     *
     * @return una representaci n en cadena del sensor.
     * */
     @Override
    public String toString() {
        if (valorUltimaLectura == null) {
            return String.format("[%s (desde: %s): %s (SIN LECTURAS)]",
                    id, fechaDeInstalacion, this.getClass().getSimpleName());
        }
        
        // Formato exacto del ejemplo: [TEMP-0001 (desde: 2023-09-01): Sensor Temperatura (20.5ºC) última lectura: 2026-01-15T10:30:00]
        return String.format("[%s (desde: %s): %s (%.1f%s) última lectura: %s]",
                id, fechaDeInstalacion, this.getClass().getSimpleName(),
                valorUltimaLectura, unidadDeLectura.getSimbolo(),
                fechaUltimaLectura);
    }

}
