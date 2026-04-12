package Sensores;

public class SensorHumedad extends Sensor {
    private static int contador = 1;

    public SensorHumedad(double offset, long horasCaducidad) {
        super(TipoSensor.HUMEDAD,"HUM-" + String.format("%04d", contador++), UnidadDeMedida.PORCENTAJE, offset, horasCaducidad);
    }

    @Override
    public boolean validarRango(double valor) {
        return valor >= 0.0 && valor <= 100.0;
    }
}