package medisystem.avanzada.uq.citas_service.exceptions;

// Se recomienda 'final'
public final class CitaNoEncontradaException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public CitaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug)
    public CitaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (el más común).
    // Usamos Long, ya que estandarizamos el ID de Cita a Long.
    public CitaNoEncontradaException(Long id) {
        super("La Cita con ID " + id + " no fue encontrada en el sistema.");
    }
}