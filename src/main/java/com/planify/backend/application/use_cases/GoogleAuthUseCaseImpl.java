package com.planify.backend.application.use_cases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class GoogleAuthUseCaseImpl implements GoogleAuthUseCase {

    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Key signingKey;

    @Value("${app.jwt.expiration-ms:3600000}") // 1 hora
    private long jwtExpirationMs;

    public GoogleAuthUseCaseImpl(UsersRepository usersRepository, @Value("${app.jwt.secret}") String jwtSecret) {
        System.out.println("✅ GoogleAuthUseCaseImpl inicializado correctamente");
        this.usersRepository = usersRepository;
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<UsersEntity> authenticate(GoogleLoginRequestDTO request) {
        try {
            System.out.println("🔍 Token recibido: " + request.getIdToken());
            // 1️⃣ Decodificar el token de Google
            String[] parts = request.getIdToken().split("\\.");
            if (parts.length < 2) {
                return Mono.error(new RuntimeException("Token inválido"));
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            var payload = objectMapper.readTree(payloadJson);
            System.out.println("📨 Payload: " + payloadJson);

            // 2️⃣ Extraer datos del token
            String sub = payload.path("sub").asText(null);
            String email = payload.path("email").asText(null);
            boolean emailVerified = payload.path("email_verified").asBoolean(false);
            String name = payload.path("name").asText("");
            String picture = payload.path("picture").asText("");

            if (sub == null || email == null) {
                return Mono.error(new RuntimeException("Token incompleto: faltan datos obligatorios (sub o email)"));
            }

            if (!emailVerified) {
                return Mono.error(new RuntimeException("Correo de Google no verificado"));
            }

            // 3️⃣ Buscar si el usuario ya existe
            return usersRepository.findByGoogleId(sub)
                    .flatMap(existingUser -> {
                        // Caso A – Usuario ya existe
                        //existingUser.setLastLogin(Instant.now());
                        String jwt = generateJwt(existingUser);
                        existingUser.setToken(jwt);
                        return usersRepository.save(existingUser);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        // Caso B – Nuevo usuario
                        UsersEntity newUser = new UsersEntity();
                        newUser.setGoogleId(sub);
                        newUser.setEmail(email);
                        newUser.setFirstname(name);
                        newUser.setProfilePicture(picture);
                        newUser.setRole("ROLE_USER");
                        //newUser.setLastLogin(Instant.now());
                        String jwt = generateJwt(newUser);
                        newUser.setToken(jwt);
                        return usersRepository.save(newUser);
                    }));

        } catch (Exception e) {
            return Mono.error(new RuntimeException("Error al procesar token de Google: " + e.getMessage(), e));
        }
    }

    private String generateJwt(UsersEntity user) {
        return Jwts.builder()
                .setSubject(user.getGoogleId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
