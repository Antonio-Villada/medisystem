package medisystem.avanzada.uq.citas_service.exceptions;

public final class CitaNoEncontradaException extends RuntimeException {

    public CitaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public CitaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public CitaNoEncontradaException(Long id) {
        super("La Cita con ID " + id + " no fue encontrada en el sistema.");
    }
}