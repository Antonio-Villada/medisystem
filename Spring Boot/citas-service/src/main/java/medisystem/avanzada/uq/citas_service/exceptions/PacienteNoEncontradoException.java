package medisystem.avanzada.uq.citas_service.exceptions;

public class PacienteNoEncontradoException extends RuntimeException {
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
