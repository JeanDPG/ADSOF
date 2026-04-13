package Excepciones;

public abstract class AlertaException extends Exception {
    public AlertaException(String mensaje) {
        super(mensaje);
    }
}
