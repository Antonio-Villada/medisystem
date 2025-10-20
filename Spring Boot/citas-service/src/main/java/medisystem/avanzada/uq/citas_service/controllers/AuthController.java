package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.repositories.UsuarioRepository;
import medisystem.avanzada.uq.citas_service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "Usuario registrado con éxito: " + usuario.getUsername();
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioDB = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioDB.isEmpty()) {
            return "Usuario no encontrado";
        }

        if (!passwordEncoder.matches(usuario.getPassword(), usuarioDB.get().getPassword())) {
            return "Contraseña incorrecta";
        }

        String token = jwtTokenProvider.generarToken(usuario.getUsername());
        return "Token JWT: " + token;
    }

    @GetMapping("/test")
    public String test() {
        return "Funciona sin autenticación!";
    }
}
