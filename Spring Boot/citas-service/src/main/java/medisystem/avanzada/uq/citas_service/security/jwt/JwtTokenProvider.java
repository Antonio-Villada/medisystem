package medisystem.avanzada.uq.citas_service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; // Necesaria para el manejo de excepciones
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final String secretString;
    private final SecretKey secretKey;
    private final long expirationTime = 86400000; // 24 horas en milisegundos

    public JwtTokenProvider(@Value("${jwt.secret}") String secretString) {
        // La clave debe tener al menos 256 bits (32 caracteres)
        this.secretString = secretString;
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    // ==========================================================
    // Generar token
    // ==========================================================
    public String generarToken(String username, List<String> roles) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ==========================================================
    // Validación del token (MÉTODO FALTANTE CORREGIDO)
    // ==========================================================
    /**
     * Valida la firma y la fecha de expiración del token.
     * @param token El JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            // Manejo de errores de validación de firma o expiración.
            return false;
        }
    }

    // ==========================================================
    // Obtener Claims
    // ==========================================================
    public String obtenerUsernameDelToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}