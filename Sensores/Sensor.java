package Sensores;


import Sensores.Estrategias.EstrategiaSimulacion;

import java.time.*;

public abstract class Sensor {
    private String id;
    private double offset;
    private double min;
    private double max;
    private TipoSensor tipo;
    private UnidadDeMedida unidadDeLectura;
    private LocalDateTime fechaUltimaLectura;
    private double valorUltimaLectura;
    private LocalDate fechaDeInstalacion;
    private LocalDateTime fechaUltimaCalibracion;
    private LocalDateTime fechaCaducidadCalibracion;
    private long duracionCalibracionDias;
    private boolean calibradoPorRango;
    private double valorLecturaAnterior;
    private double porcentajeCambioBrusco;
    private boolean medicionDetenida;
    private EstrategiaSimulacion estrategia;

    // Constructor base (privado) - inicializa todos los campos
    private Sensor(TipoSensor tipo, String id, double offset, long duracionCalibracion,
                   UnidadDeMedida unidad, EstrategiaSimulacion estrategia) {
        this.tipo = tipo;
        this.id = id;
        this.offset = offset;
        this.duracionCalibracionDias = duracionCalibracion;
        this.fechaCaducidadCalibracion = this.fechaUltimaCalibracion.plusDays(this.duracionCalibracionDias);
        this.unidadDeLectura = unidad;
        this.estrategia = estrategia;
        this.fechaDeInstalacion = LocalDate.now();
        this.fechaUltimaCalibracion = LocalDateTime.now();
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


    public abstract boolean validarRango(double valor);

    /**
     * Toma una lectura del sensor y la procesa.
     * El valor tomado se resta al offset del sensor y se
     * valida si se encuentra dentro del rango permitido.
     * Si no se encuentra dentro del rango, se marca como
     * no calibrado.
     * @param valorMedido el valor medido por el sensor
     */
    public void tomarLectura(double valorMedido) {
        double valorFinal = valorMedido - this.offset;
        if (!validarRango(valorFinal)) {
            this.calibradoPorRango = false;
        }
        this.valorUltimaLectura = valorFinal;
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
        // Formato: [TEMP-0001 (desde: 2023-09-01): Sensores.Sensor Temperatura (20.5ºC) ...]
        return String.format("[%s (desde: %s): %s (%.1f%s) última lectura: %s]",
                id, fechaDeInstalacion, this.getClass().getSimpleName(),
                valorUltimaLectura, unidadDeLectura,
                fechaUltimaLectura != null ? fechaUltimaLectura : "SIN LECTURAS");
    }

}
