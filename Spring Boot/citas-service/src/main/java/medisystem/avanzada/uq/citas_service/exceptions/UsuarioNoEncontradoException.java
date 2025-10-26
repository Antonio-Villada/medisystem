package medisystem.avanzada.uq.citas_service.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {

    // ESTE ES EL CONSTRUCTOR NECESARIO:
    public UsuarioNoEncontradoException(String message) {
        super(message); // Llama al constructor de RuntimeException para establecer el mensaje.
    }

    // Puedes añadir otros, pero este es el mínimo requerido para el orElseThrow
    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}