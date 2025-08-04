package com.planify.backend.infrastructure.security.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenValidator implements ValidateFirebaseTokenUseCase {

    @Override
    public FirebaseUser execute(String idToken) {
        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return new FirebaseUser(token.getUid(), token.getEmail(), token.isEmailVerified());
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }

}
