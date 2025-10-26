package medisystem.avanzada.uq.citas_service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtTokenProvider.validarToken(token)) {

                // 1. Extraer claims
                Claims claims = Jwts.parser()
                        .verifyWith(jwtTokenProvider.getSecretKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.getSubject();
                // NOTA: Los roles en el payload del JWT son el nombre limpio (ADMINISTRADOR)
                List<String> roles = claims.get("roles", List.class);

                // 2. Convertir los roles en autoridades de Spring
                // ¡CORRECCIÓN CLAVE! Ya no añadimos "ROLE_", ya que la BD no lo tiene
                // y el SecurityConfig fue ajustado con GrantedAuthorityDefaults("").
                List<SimpleGrantedAuthority> authorities = roles != null
                        ? roles.stream()
                        .map(r -> new SimpleGrantedAuthority(r)) // <-- Se quitó "ROLE_" +
                        .collect(Collectors.toList())
                        : Collections.emptyList();

                // 3. Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                new User(username, "", authorities), // Principio de seguridad
                                null,
                                authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
