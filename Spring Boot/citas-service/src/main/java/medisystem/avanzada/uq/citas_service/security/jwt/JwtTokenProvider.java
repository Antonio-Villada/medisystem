package medisystem.avanzada.uq.citas_service.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "clave-super-segura-de-256-bits-para-el-jwt-medissystem-123456".getBytes()
    );
    private final long expirationTime = 86400000; // 24 horas

    public SecretKey getSecretKey() {
        return secretKey;
    }

    // ==========================================================
    // Generar token con username y roles
    // ==========================================================
    public String generarToken(String username, List<String> roles) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles) // 👈 Se agregan los roles al token
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ==========================================================
    // Obtener el username del token
    // ==========================================================
    public String obtenerUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // ==========================================================
    // Validar si el token es correcto y no expiró
    // ==========================================================
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
