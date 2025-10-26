package medisystem.avanzada.uq.citas_service.exceptions;

public final class PacienteNoEncontradoException extends RuntimeException {

    // 1. ELIMINADO/COMENTADO: public PacienteNoEncontradoException(String mensaje) {
    //    Este constructor es el que entra en conflicto con el Constructor 3 (identificacion).
    // }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public PacienteNoEncontradoException(String mensaje, Throwable causa) {
        // Mantenemos este constructor ya que su firma es única (String, Throwable)
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Identificación/Cédula)
    // Este constructor ahora manejará todos los casos donde se pase un String simple.
    // El ID de Paciente es un String.
    public PacienteNoEncontradoException(String identificacion) {
        super("El Paciente con identificación " + identificacion + " no fue encontrado en el sistema.");
    }

    // 4. Constructor especializado para buscar por Correo Electrónico (clave de negocio)
    // Mantenemos este constructor ya que su firma es única (String, boolean)
    public PacienteNoEncontradoException(String correo, boolean esCorreo) {
        super("El Paciente con correo " + correo + " no fue encontrado en el sistema.");
    }
}