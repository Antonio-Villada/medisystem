package medisystem.avanzada.uq.citas_service.exceptions;

public final class EpsNoEncontradaException extends RuntimeException {

    public EpsNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public EpsNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public EpsNoEncontradaException(Long id) {
        super("La EPS con ID " + id + " no fue encontrada en el sistema.");
    }

    public EpsNoEncontradaException(String nombre, boolean esNombre) {
        super("La EPS '" + nombre + "' no fue encontrada en el sistema.");
    }
}