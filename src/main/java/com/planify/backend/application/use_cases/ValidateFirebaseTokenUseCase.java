package com.planify.backend.application.use_cases;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.domain.models.FirebaseUser;
import reactor.core.publisher.Mono;

public interface ValidateFirebaseTokenUseCase {

    Mono<FirebaseUser> execute(String idToken) throws FirebaseAuthException;

}
