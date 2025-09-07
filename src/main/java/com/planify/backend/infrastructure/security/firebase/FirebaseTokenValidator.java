package com.planify.backend.infrastructure.security.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FirebaseTokenValidator implements ValidateFirebaseTokenUseCase {

    @Override
    public Mono<FirebaseUser> execute(String idToken) {
        return Mono.fromCallable(() -> {
                    FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
                    return new FirebaseUser(
                            token.getUid(),
                            token.getEmail(),
                            token.isEmailVerified()
                    );
                })
                .subscribeOn(Schedulers.boundedElastic()) // evita bloquear el hilo principal
                .onErrorMap(FirebaseAuthException.class, e ->
                        new RuntimeException("Token inválido o expirado", e));
    }
}
