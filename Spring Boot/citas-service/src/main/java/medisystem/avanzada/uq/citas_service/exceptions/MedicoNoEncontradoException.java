package medisystem.avanzada.uq.citas_service.exceptions;

public class MedicoNoEncontradoException extends RuntimeException {
    public MedicoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
