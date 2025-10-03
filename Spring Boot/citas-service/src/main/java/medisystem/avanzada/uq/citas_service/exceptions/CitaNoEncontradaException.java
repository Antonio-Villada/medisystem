package medisystem.avanzada.uq.citas_service.exceptions;

public class CitaNoEncontradaException extends RuntimeException {
    public CitaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
