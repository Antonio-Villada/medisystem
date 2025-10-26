package medisystem.avanzada.uq.citas_service.exceptions;

public final class DetalleFormulaNoEncontradaException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public DetalleFormulaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public DetalleFormulaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    // El ID de DetalleFormula fue estandarizado a Long.
    public DetalleFormulaNoEncontradaException(Long id) {
        super("El Detalle de Fórmula con ID " + id + " no fue encontrado en el sistema.");
    }
}