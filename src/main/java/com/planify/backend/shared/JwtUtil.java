package com.planify.backend.shared;
import com.planify.backend.domain.models.UsersEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";
    private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000;
    private static final String TOKEN_PREFIX = "Bearer ";

    private final SecretKey key;
    private final JwtParser jwtParser;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /** ✅ Crear token JWT desde un UsersEntity */
    public Mono<String> createToken(UsersEntity user) {
        return Mono.fromSupplier(() -> {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY);

            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .setClaims(Map.of(
                            "id", user.getId(),
                            "role", user.getRole(),
                            "firstname", user.getFirstname(),
                            "lastname", user.getLastname()
                    ))
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        });
    }

    /** ✅ Extraer token desde un header Authorization */
    public Mono<String> resolveToken(String authHeader) {
        return Mono.justOrEmpty(authHeader)
                .filter(h -> h.startsWith(TOKEN_PREFIX))
                .map(h -> h.substring(TOKEN_PREFIX.length()));
    }

    /** ✅ Parsear y validar las claims del token */
    public Mono<Claims> parseClaims(String token) {
        return Mono.fromCallable(() -> jwtParser.parseClaimsJws(token).getBody())
                .onErrorResume(e -> {
                    if (e instanceof ExpiredJwtException ex) {
                        return Mono.error(new RuntimeException("Token expirado: " + ex.getMessage()));
                    }
                    return Mono.error(new RuntimeException("Token inválido: " + e.getMessage()));
                });
    }

    /** ✅ Validar expiración */
    public Mono<Boolean> validateClaims(Claims claims) {
        return Mono.just(claims.getExpiration().after(new Date()));
    }

    /** ✅ Extraer el email desde las claims */
    public Mono<String> getEmail(Claims claims) {
        return Mono.justOrEmpty(claims.getSubject());
    }

    /** ✅ Extraer usuario desde token */
    public Mono<UsersEntity> extractUserFromToken(String token) {
        return parseClaims(token)
                .map(claims -> UsersEntity.builder()
                        .id(((Number) claims.get("id")).longValue())
                        .email(claims.getSubject())
                        .role((String) claims.get("role"))
                        .firstname((String) claims.get("firstname"))
                        .lastname((String) claims.get("lastname"))
                        .build());
    }
}
