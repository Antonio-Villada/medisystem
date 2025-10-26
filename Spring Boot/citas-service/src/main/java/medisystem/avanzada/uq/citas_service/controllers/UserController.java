package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================================================
    // GET /api/usuarios/perfil : Obtener el perfil del usuario autenticado
    // ==========================================================

    /**
     * Permite a cualquier usuario autenticado ver su propio perfil (sin contraseña).
     * El username se extrae automáticamente del token/sesión de Spring Security.
     */
    @GetMapping("/perfil")
    @PreAuthorize("isAuthenticated()") // Permite cualquier rol que haya iniciado sesión
    public ResponseEntity<UsuarioResponseDTO> getPerfilUsuario() {
        // 1. Obtener el nombre de usuario (username) del contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Delegar la búsqueda y mapeo al servicio
        UsuarioResponseDTO perfil = userService.getPerfilUsuario(username);

        return ResponseEntity.ok(perfil);
    }

    // NOTA: Los endpoints de POST (Registro) para Usuarios son delegados a
    // MedicoController y PacienteController, que usan el UserService internamente.

    // Si fuera necesario un endpoint para cambiar la contraseña o actualizar el username,
    // se añadirían métodos PUT/PATCH que interactuarían con el UserService.
}