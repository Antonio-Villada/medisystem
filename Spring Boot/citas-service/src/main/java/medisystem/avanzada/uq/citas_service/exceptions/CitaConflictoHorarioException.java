package medisystem.avanzada.uq.citas_service.exceptions;

public final class CitaConflictoHorarioException extends RuntimeException {

    // 1. Constructor principal con mensaje detallado del conflicto (mantenido por necesidad).
    // La capa de Servicio debe construir un mensaje específico (Ej: "El médico X está ocupado de 10:00 a 11:00").
    public CitaConflictoHorarioException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug).
    public CitaConflictoHorarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}