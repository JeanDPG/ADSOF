package Sensores;

public class SensorDuplicadoException extends Exception {
    private Sensor sensorExistente;
    private Sensor sensorNuevo;

    public SensorDuplicadoException(Sensor existente, Sensor nuevo) {
        super("Error: Ya existe un sensor con el ID " + existente.getId());
        this.sensorExistente = existente;
        this.sensorNuevo = nuevo;
    }

    public Sensor getSensorExistente() { return sensorExistente; }
    public Sensor getSensorNuevo() { return sensorNuevo; }
}