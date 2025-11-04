package medisystem.avanzada.uq.citas_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importación necesaria
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- MANEJADOR UNIFICADO PARA EXCEPCIONES DE 'RECURSO NO ENCONTRADO' (404 NOT_FOUND) ---
    @ExceptionHandler({
            MedicoNoEncontradoException.class,
            EpsNoEncontradaException.class,
            EspecialidadNoEncontradaException.class,
            TelefonoNoEncontradoException.class,
            PacienteNoEncontradoException.class,
            CitaNoEncontradaException.class,
            MedicamentoNoEncontradoException.class,
            FormulaNoEncontradaException.class,
            DetalleFormulaNoEncontradaException.class,
            UsuarioNoEncontradoException.class,
            RolNoEncontradoException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(RuntimeException ex) {
        return buildErrorResponse(
                ex,
                HttpStatus.NOT_FOUND,
                "Not Found"
        );
    }

    // --- MANEJADOR UNIFICADO PARA EXCEPCIONES DE 'CONFLICTO DE NEGOCIO' (409 CONFLICT) ---
    @ExceptionHandler({
            UsuarioYaExisteException.class,
            PacienteYaExisteException.class,
            MedicoYaExisteException.class,
            MedicamentoYaExisteException.class,
            CitaConflictoHorarioException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflictExceptions(RuntimeException ex) {
        return buildErrorResponse(
                ex,
                HttpStatus.CONFLICT,
                "Conflict"
        );
    }

    // --- MANEJADOR ESPECÍFICO DE AUTENTICACIÓN (401 UNAUTHORIZED) ---

    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class // Cuando el UserDetailsService no encuentra el usuario
    })
    public ResponseEntity<Map<String, Object>> handleAuthenticationExceptions(RuntimeException ex) {
        // Usamos el mensaje de la excepción de seguridad
        String message = (ex.getMessage() != null && !ex.getMessage().isEmpty())
                ? ex.getMessage()
                : "Credenciales inválidas o usuario no encontrado.";

        // Retornamos 401 Unauthorized
        return buildErrorResponse(
                new RuntimeException(message),
                HttpStatus.UNAUTHORIZED,
                "Unauthorized"
        );
    }

    // --- MANEJADOR PARA SOLICITUDES INVÁLIDAS (400 BAD_REQUEST) ---
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                "Bad Request"
        );
    }

    // --- MANEJADOR GENERAL DE ERRORES INTERNOS (500 INTERNAL_SERVER_ERROR) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return buildErrorResponse(
                ex,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error"
        );
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            Exception ex,
            HttpStatus status,
            String errorType) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", errorType);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }
}