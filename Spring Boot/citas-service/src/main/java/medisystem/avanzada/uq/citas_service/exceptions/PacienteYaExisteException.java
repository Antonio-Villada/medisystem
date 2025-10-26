package medisystem.avanzada.uq.citas_service.exceptions;

public final class PacienteYaExisteException extends RuntimeException {

    // 1. ELIMINADO/COMENTADO: public PacienteYaExisteException(String mensaje) {
    //    Este constructor es el que entra en conflicto con el Constructor 3 (identificacion).
    // }

    // 2. Constructor que envuelve otra excepción (para debug)
    public PacienteYaExisteException(String mensaje, Throwable causa) {
        // Mantenemos este constructor ya que su firma es única (String, Throwable)
        super(mensaje, causa);
    }

    // 3. Constructor especializado para indicar que la identificación (ID) ya existe.
    // Este constructor ahora maneja todos los casos donde se pase un String simple.
    // El ID de Paciente es un String (la cédula).
    public PacienteYaExisteException(String identificacion) {
        super("El Paciente con identificación '" + identificacion + "' ya se encuentra registrado.");
    }

    // 4. Constructor especializado para indicar que el correo electrónico ya existe.
    // Mantenemos este constructor ya que su firma es única (String, boolean)
    public PacienteYaExisteException(String correo, boolean porCorreo) {
        super("El correo electrónico '" + correo + "' ya está asociado a otro paciente.");
    }
}