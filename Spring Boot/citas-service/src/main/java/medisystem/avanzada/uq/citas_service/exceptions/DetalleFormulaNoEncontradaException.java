package medisystem.avanzada.uq.citas_service.exceptions;

public final class DetalleFormulaNoEncontradaException extends RuntimeException {

    public DetalleFormulaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public DetalleFormulaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public DetalleFormulaNoEncontradaException(Long id) {
        super("El Detalle de Fórmula con ID " + id + " no fue encontrado en el sistema.");
    }
}