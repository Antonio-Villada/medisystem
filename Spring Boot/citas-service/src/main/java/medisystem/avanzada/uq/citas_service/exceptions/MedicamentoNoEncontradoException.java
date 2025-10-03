package medisystem.avanzada.uq.citas_service.exceptions;

public class MedicamentoNoEncontradoException extends RuntimeException {
    public MedicamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
