package medisystem.avanzada.uq.citas_service.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import medisystem.avanzada.uq.citas_service.entities.Rol;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.repositories.RolRepository;
import medisystem.avanzada.uq.citas_service.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component("dataInitializerSecurity")
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository,
                               RolRepository rolRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<UsuarioJson>> typeRef = new TypeReference<>() {};

            try (InputStream inputStream = getClass().getResourceAsStream("/data/usuarios.json")) {
                if (inputStream == null) {
                    System.err.println(" No se encontró el archivo usuarios.json");
                    return;
                }

                List<UsuarioJson> usuariosJson = mapper.readValue(inputStream, typeRef);

                for (UsuarioJson u : usuariosJson) {
                    if (usuarioRepository.existsByUsername(u.getUsername())) continue;

                    Usuario usuario = new Usuario();
                    usuario.setUsername(u.getUsername());
                    usuario.setPassword(passwordEncoder.encode(u.getPassword()));

                    for (String rolNombre : u.getRoles()) {
                        Rol rol = rolRepository.findByNombre(rolNombre)
                                .orElseGet(() -> rolRepository.save(new Rol(rolNombre)));
                        usuario.getRoles().add(rol);
                    }

                    usuarioRepository.save(usuario);
                    System.out.println("✅ Usuario creado: " + u.getUsername());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
