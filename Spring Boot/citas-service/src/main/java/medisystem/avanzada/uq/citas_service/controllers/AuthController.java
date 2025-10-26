package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.auth.LoginRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.auth.LoginResponseDTO; // Asumido
import medisystem.avanzada.uq.citas_service.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Cambiamos la inyección para usar el AuthenticationManager estándar
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ==========================================================
    // POST /auth/login : Inicio de sesión y generación de token
    // ==========================================================

    @PostMapping("/login")
    // Usamos el DTO de entrada y devolvemos el DTO de respuesta estándar
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {

        try {
            // 1. Intentar autenticar las credenciales (lanza excepción si falla)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 2. Obtener los detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. Obtener roles en formato String para el token
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.toList());

            // 4. Generar el token JWT
            String jwt = jwtTokenProvider.generarToken(userDetails.getUsername(), roles);

            // 5. Devolver la respuesta con el token
            LoginResponseDTO response = new LoginResponseDTO(jwt);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            // Si la autenticación falla (ej: BadCredentialsException), se captura y se devuelve 401 Unauthorized.
            throw new ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED, "Credenciales inválidas o usuario no encontrado.");
        }
    }

    // NOTA: El registro es manejado por MedicoController y PacienteController
    // El endpoint /test se elimina o se mueve a un Controller de testing

}