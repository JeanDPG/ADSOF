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
    private Duration tiempoCaducidadCalibracion;
    private boolean calibradoPorRango;
    private EstrategiaSimulacion estrategia;

    // Constructor base (privado) - inicializa todos los campos
    private Sensor(TipoSensor tipo, String id, double offset, long horasCaducidad,
                   UnidadDeMedida unidad, EstrategiaSimulacion estrategia) {
        this.tipo = tipo;
        this.id = id;
        this.offset = offset;
        this.tiempoCaducidadCalibracion = Duration.ofHours(horasCaducidad);
        this.unidadDeLectura = unidad;
        this.estrategia = estrategia;
        this.fechaDeInstalacion = LocalDate.now();
        this.fechaUltimaCalibracion = LocalDateTime.now();
        this.calibradoPorRango = true;
    }

    // Constructor 1: Parámetros básicos sin unidad ni estrategia
    public Sensor(TipoSensor tipo, String id, double offset, long horasCaducidad) {
        this(tipo, id, offset, horasCaducidad, null, null);
    }

    // Constructor 2: Con estrategia de simulación
    public Sensor(TipoSensor tipo, String id, double offset, long horasCaducidad,
                  EstrategiaSimulacion estrategia) {
        this(tipo, id, offset, horasCaducidad, null, estrategia);
    }

    // Constructor 3: Con unidad de medida
    public Sensor(TipoSensor tipo, String id, UnidadDeMedida unidad, double offset,
                  long horasCaducidad) {
        this(tipo, id, offset, horasCaducidad, unidad, null);
    }

    // Constructor 4: Con unidad de medida y estrategia
    public Sensor(TipoSensor tipo, String id, UnidadDeMedida unidad, double offset,
                  long horasCaducidad, EstrategiaSimulacion estrategia) {
        this(tipo, id, offset, horasCaducidad, unidad, estrategia);
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
        if (!calibradoPorRango) return false;
        Duration transcurrido = Duration.between(fechaUltimaCalibracion, LocalDateTime.now());
        return transcurrido.compareTo(tiempoCaducidadCalibracion) <= 0;
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
