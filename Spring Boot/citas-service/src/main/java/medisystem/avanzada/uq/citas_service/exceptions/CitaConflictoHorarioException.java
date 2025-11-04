package medisystem.avanzada.uq.citas_service.exceptions;

public final class CitaConflictoHorarioException extends RuntimeException {

    public CitaConflictoHorarioException(String mensaje) {
        super(mensaje);
    }

    public CitaConflictoHorarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}