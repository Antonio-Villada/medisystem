package medisystem.avanzada.uq.citas_service.exceptions;

public final class PacienteYaExisteException extends RuntimeException {

    public PacienteYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public PacienteYaExisteException(String identificacion) {
        super("El Paciente con identificación '" + identificacion + "' ya se encuentra registrado.");
    }

    public PacienteYaExisteException(String correo, boolean porCorreo) {
        super("El correo electrónico '" + correo + "' ya está asociado a otro paciente.");
    }
}