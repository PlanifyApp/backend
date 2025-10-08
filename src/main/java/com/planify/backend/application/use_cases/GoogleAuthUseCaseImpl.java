//// src/main/java/com/planify/backend/application/use_cases/GoogleAuthUseCaseImpl.java
//package com.planify.backend.application.use_cases;
//
//import com.google.firebase.auth.FirebaseAuthException;
//import com.planify.backend.application.dtos.LoginResponseDTO;
//import com.planify.backend.domain.models.UsersEntity;
//import com.planify.backend.infrastructure.repositories.UsersRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class GoogleAuthUseCaseImpl implements GoogleAuthUseCase {
//
//    private final ValidateFirebaseTokenUseCase validateFirebaseTokenUseCase; // ya existe en tu proyecto
//    private final UsersRepository usersRepository;
//
//@Override
//public Mono<LoginResponseDTO> execute(String idToken) {
//    return Mono.fromCallable(() -> validateFirebaseTokenUseCase.execute(idToken))
//        .flatMap(mono -> mono) // porque execute() ya devuelve un Mono
//        .flatMap(firebaseUser ->
//            usersRepository.findByGoogleId(firebaseUser.uid())
//                .switchIfEmpty(Mono.defer(() -> {
//                    UsersEntity newUser = new UsersEntity();
//                    newUser.setGoogleId(firebaseUser.uid());
//                    newUser.setEmail(firebaseUser.email());
//                    newUser.setRole("ROLE_USER");
//                    newUser.setCreatedAt(LocalDateTime.now());
//                    return usersRepository.save(newUser);
//                }))
//                .flatMap(savedUser -> usersRepository.save(savedUser)
//                        .onErrorResume(e -> Mono.just(savedUser)))
//                .map(savedUser -> new LoginResponseDTO(firebaseUser.uid(), savedUser))
//        )
//        .onErrorResume(FirebaseAuthException.class, e -> {
//            // Si el token es inválido o expira, devuelves error controlado
//            return Mono.error(new RuntimeException("Token de Firebase inválido o expirado: " + e.getMessage()));
//        });
//    }
//
//}
