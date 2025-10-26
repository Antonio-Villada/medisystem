package medisystem.avanzada.uq.citas_service.exceptions;

public final class RolNoEncontradoException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public RolNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public RolNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    public RolNoEncontradoException(Long id) {
        super("El Rol con ID " + id + " no fue encontrado en el sistema.");
    }

    // 4. Constructor especializado para buscar por Nombre (Clave de Negocio: String, Ej: "ROLE_MEDICO")
    public RolNoEncontradoException(String nombre, boolean porNombre) {
        // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
        super("El Rol con nombre '" + nombre + "' no fue encontrado.");
    }
}