package medisystem.avanzada.uq.citas_service.security;

import medisystem.avanzada.uq.citas_service.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Swagger y documentación
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs.json"
                        ).permitAll()

                        // 🔓 Autenticación pública
                        .requestMatchers("/auth/**").permitAll()

                        // 🔒 Reglas específicas por rol
                        .requestMatchers("/medicos/**", "/pacientes/**", "/telefonos/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/eps/**", "/Especialidad/**", "/medicamentos/**").hasAnyRole("MEDICO", "ADMINISTRADOR")
                        .requestMatchers("/citas/**").hasAnyRole("PACIENTE", "MEDICO", "ADMINISTRADOR")
                        .requestMatchers("/formulas/**", "/detalle-formulas/**").hasAnyRole("PACIENTE", "MEDICO", "ADMINISTRADOR")

                        // 🔒 Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
