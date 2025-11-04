package medisystem.avanzada.uq.citas_service.exceptions;

public final class EspecialidadNoEncontradaException extends RuntimeException {

    public EspecialidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public EspecialidadNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public EspecialidadNoEncontradaException(Long id) {
        super("La Especialidad con ID " + id + " no fue encontrada en el sistema.");
    }

    public EspecialidadNoEncontradaException(String nombre, boolean esNombre) {
        super("La Especialidad '" + nombre + "' no fue encontrada en el sistema.");
    }
}