package com.planify.backend.presentation.controllers;
import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.application.dtos.UpdateUserDTO;
import com.planify.backend.application.use_cases.UsersService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Object>> register(@Valid @RequestBody RegisterUserDTO user) {
        log.info("📩 [POST] Register user: {}", user.getEmail());

        return usersService.registerUser(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body((Object) savedUser))
                .onErrorResume(error -> {
                    log.error("❌ Error registering user: {}", error.getMessage(), error);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body((Object) new ErrorResponse("Error interno al registrar el usuario", error.getMessage())));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> getUserById(@PathVariable Integer id) {
        log.info("🔍 [GET] Fetching user with ID: {}", id);
        return usersService.getUserById(id)
                .map(user -> {
                    log.info("✅ User found: {}", user.getEmail());
                    return ResponseEntity.ok((Object) user);
                })
                .onErrorResume(error -> {
                    log.error("⚠️ Error fetching user {}: {}", id, error.getMessage(), error);
                    String message = error.getMessage().contains("no encontrado")
                            ? "Usuario no encontrado"
                            : "Error interno al obtener el usuario";
                    HttpStatus status = message.equals("Usuario no encontrado")
                            ? HttpStatus.NOT_FOUND
                            : HttpStatus.INTERNAL_SERVER_ERROR;
                    return Mono.just(ResponseEntity.status(status)
                            .body((Object) new ErrorResponse(message, error.getMessage())));
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserDTO dto,
            @RequestParam(required = false) String photoUrl
    ) {
        log.info("📝 [PUT] Updating user ID {} with data: {}", id, dto);
        return usersService.updateUser(id, dto, photoUrl)
                .map(updated -> {
                    log.info("✅ User {} updated successfully", id);
                    return ResponseEntity.ok((Object) updated);
                })
                .onErrorResume(error -> {
                    log.error("❌ Error updating user {}: {}", id, error.getMessage(), error);
                    String message = error.getMessage().contains("no encontrado")
                            ? "Usuario no encontrado"
                            : "Error interno al actualizar usuario";
                    HttpStatus status = message.equals("Usuario no encontrado")
                            ? HttpStatus.NOT_FOUND
                            : HttpStatus.INTERNAL_SERVER_ERROR;
                    return Mono.just(ResponseEntity.status(status)
                            .body((Object) new ErrorResponse(message, error.getMessage())));
                });
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable Integer id) {
        log.info("🗑️ [DELETE] Deleting user with ID: {}", id);
        return usersService.deleteUser(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(error -> {
                    log.error("❌ Error deleting user {}: {}", id, error.getMessage(), error);
                    String message = error.getMessage().contains("no encontrado")
                            ? "Usuario no encontrado"
                            : "Error interno al eliminar usuario";
                    HttpStatus status = message.equals("Usuario no encontrado")
                            ? HttpStatus.NOT_FOUND
                            : HttpStatus.INTERNAL_SERVER_ERROR;
                    return Mono.just(ResponseEntity.status(status)
                            .body((Object) new ErrorResponse(message, error.getMessage())));
                });
    }

    record ErrorResponse(String message, String details) {}
}
