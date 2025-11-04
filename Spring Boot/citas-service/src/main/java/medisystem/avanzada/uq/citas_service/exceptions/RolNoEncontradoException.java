package medisystem.avanzada.uq.citas_service.exceptions;

public final class RolNoEncontradoException extends RuntimeException {

    public RolNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public RolNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public RolNoEncontradoException(Long id) {
        super("El Rol con ID " + id + " no fue encontrado en el sistema.");
    }

    public RolNoEncontradoException(String nombre, boolean porNombre) {
        super("El Rol con nombre '" + nombre + "' no fue encontrado.");
    }
}