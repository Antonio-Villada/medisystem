package medisystem.avanzada.uq.citas_service.security;

import medisystem.avanzada.uq.citas_service.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.core.GrantedAuthorityDefaults; // 🌟 Nuevo Import

// Importaciones requeridas para el PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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
                .csrf(AbstractHttpConfigurer::disable)
                // Es crucial configurar el manejo de sesión como STATELESS para una API con JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Swagger y documentación
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs.json"
                        ).permitAll()

                        // 🔓 Autenticación pública (Ej: /sistema/api/v1/auth/login)
                        .requestMatchers("/auth/**").permitAll()

                        // 🔒 Reglas específicas por rol (Los nombres en BD: ADMINISTRADOR, MEDICO, PACIENTE)
                        .requestMatchers(
                                "/medicos/**",
                                "/pacientes/**",
                                "/telefonos/**",
                                "/usuarios/**"
                        ).hasRole("ADMINISTRADOR")
                        .requestMatchers(
                                "/eps/**",
                                "/especialidades/**",
                                "/medicamentos/**"
                        ).hasAnyRole("MEDICO", "ADMINISTRADOR")
                        .requestMatchers(
                                "/citas/**",
                                "/formulas/**",
                                "/detalle-formulas/**"
                        ).hasAnyRole("PACIENTE", "MEDICO", "ADMINISTRADOR")

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

    /**
     * Bean clave: Le dice a Spring Security que use una cadena vacía ("") como prefijo de autoridad.
     * Esto hace que hasRole("ADMINISTRADOR") coincida EXACTAMENTE con el rol "ADMINISTRADOR" de la BD,
     * respetando la restricción de tu base de datos.
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
