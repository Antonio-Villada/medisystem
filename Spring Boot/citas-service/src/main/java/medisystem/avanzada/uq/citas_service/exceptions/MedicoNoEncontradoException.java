package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicoNoEncontradoException extends RuntimeException {

    // 1. ELIMINADO/COMENTADO: public MedicoNoEncontradoException(String mensaje) {
    //    Este constructor es el que entra en conflicto con el Constructor 4 (username).
    // }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public MedicoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    public MedicoNoEncontradoException(Long id) {
        super("El Médico con ID " + id + " no fue encontrado en el sistema.");
    }

    // 4. Constructor especializado para buscar por Username (Clave de Autenticación)
    // Este constructor ahora manejará todos los casos donde se pase un String simple.
    public MedicoNoEncontradoException(String username) {
        super("El Médico asociado al username '" + username + "' no fue encontrado.");
    }

    // 5. Constructor especializado para buscar por Correo Electrónico (Clave de Negocio)
    // Usa el booleano 'esCorreo' para diferenciar su firma del Constructor 4.
    public MedicoNoEncontradoException(String correo, boolean esCorreo) {
        super("El Médico con correo '" + correo + "' no fue encontrado en el sistema.");
    }
}