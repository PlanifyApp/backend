package com.planify.backend.application.use_cases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * ✅ GoogleAuthService
 * Fusiona y reemplaza GoogleAuthUseCase y GoogleAuthUseCaseImpl.
 * Usa la tabla auth_methods en lugar de google_id dentro de users.
 */
@Service
public class GoogleAuthService {

    private final UsersRepository usersRepository;
    private final AuthMethodsRepository authMethodsRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Key signingKey;

    @Value("${app.jwt.expiration-ms:3600000}") // 1 hora
    private long jwtExpirationMs;

    public GoogleAuthService(
            UsersRepository usersRepository,
            AuthMethodsRepository authMethodsRepository,
            @Value("${app.jwt.secret}") String jwtSecret
    ) {
        this.usersRepository = usersRepository;
        this.authMethodsRepository = authMethodsRepository;
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        System.out.println("\nGoogleAuthService inicializado correctamente");
    }

    /**
     * Autentica a un usuario mediante un token de Google.
     */
    public Mono<UsersEntity> authenticate(GoogleLoginRequestDTO request) {
        try {
            System.out.println("\nIniciando autenticación con Google...");

            String idToken = request.getIdToken();
            System.out.println("Token recibido: " + idToken);

            // 1️⃣ Decodificar el token JWT de Google
            String[] parts = idToken.split("\\.");
            if (parts.length < 2) {
                System.out.println("Token inválido (formato incorrecto)");
                return Mono.error(new RuntimeException("Token inválido"));
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            JsonNode payload = objectMapper.readTree(payloadJson);
            System.out.println("Payload: " + payloadJson);

            String sub = payload.path("sub").asText(null);
            String email = payload.path("email").asText(null);
            boolean emailVerified = payload.path("email_verified").asBoolean(false);
            String name = payload.path("name").asText("");
            String picture = payload.path("picture").asText("");

            if (sub == null || email == null) {
                System.out.println("Token incompleto: faltan sub o email");
                return Mono.error(new RuntimeException("Token incompleto"));
            }

            if (!emailVerified) {
                System.out.println("Correo no verificado: " + email);
                return Mono.error(new RuntimeException("Correo no verificado"));
            }

            System.out.println("Buscando auth_method existente para Google...");

            // 2️⃣ Buscar si existe un AuthMethod vinculado al Google "sub"
            return authMethodsRepository.findByProviderUserIdAndProvider(sub, "google")
            .flatMap(authMethod -> {
                System.out.println("AuthMethod existente para googleId: " + sub);
                // Recuperamos el usuario y actualizamos last_login, etc.
                return usersRepository.findById(authMethod.getUserId().longValue())
                    .flatMap(existingUser -> {
                        System.out.println("Usuario existente: " + existingUser.getEmail());
                        existingUser.setProfilePicture(picture);
                        existingUser.setRole("ROLE_USER");
                        String jwt = generateJwt(existingUser);
                        existingUser.setToken(jwt);
                        existingUser.setCreatedAt(java.time.LocalDateTime.now()); // opcional
                        return usersRepository.save(existingUser);
                    });
            })
            .switchIfEmpty(Mono.defer(() -> {
                System.out.println("Usuario nuevo detectado (Google ID no encontrado)");
                // Creamos nuevo usuario
                UsersEntity newUser = new UsersEntity();
                String[] nameParts = name.split(" ", 2);
                newUser.setProfilePicture(picture);
                newUser.setFirstname(nameParts.length > 0 ? nameParts[0] : name);
                newUser.setLastname(nameParts.length > 1 ? nameParts[1] : "User Last Name");
                newUser.setGender(UsersEntity.GenderEnum.other);
                newUser.setUsername(email.split("@")[0]); // valor por defecto: texto antes del @
                newUser.setRole("ROLE_USER");
                newUser.setAddress("N/A"); // valor por defecto temporal
                newUser.setEmail(email);
                newUser.setCreatedAt(java.time.LocalDateTime.now());

                // Guardamos usuario y luego creamos su AuthMethod
                return usersRepository.save(newUser)
                    .flatMap(savedUser -> {
                        AuthMethodsEntity auth = new AuthMethodsEntity();
                        auth.setUserId(savedUser.getId().intValue());
                        auth.setProvider("google");
                        auth.setProviderUserId(sub);
                        auth.setCreatedAt(java.time.LocalDateTime.now());

                        return authMethodsRepository.save(auth)
                            .thenReturn(savedUser)
                            .map(u -> {
                                String jwt = generateJwt(u);
                                u.setToken(jwt);
                                return u;
                            });
                    });
            }))
            .doOnError(err -> System.out.println("\nError en authenticate(): " + err.getMessage()));
        } catch (Exception e) {
            System.out.println("Excepción en authenticate(): " + e.getMessage());
            return Mono.error(new RuntimeException("Error procesando token de Google", e));
        }
    }

    private String generateJwt(UsersEntity user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
