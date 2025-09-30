package com.planify.backend.infrastructure.security.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FirebaseTokenValidator implements ValidateFirebaseTokenUseCase {

    private final FirebaseAuth firebaseAuth;

    public FirebaseTokenValidator(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Mono<FirebaseUser> execute(String token) {
        return Mono.fromCallable(() -> {
            FirebaseToken decoded = firebaseAuth.verifyIdToken(token);
            UserRecord user = firebaseAuth.getUser(decoded.getUid());
            return new FirebaseUser(user.getUid(), user.getEmail());
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
