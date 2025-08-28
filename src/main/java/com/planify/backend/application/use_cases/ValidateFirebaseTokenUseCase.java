package com.planify.backend.application.use_cases;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.domain.models.FirebaseUser;

public interface ValidateFirebaseTokenUseCase {

    public FirebaseUser execute(String idToken) throws FirebaseAuthException;

}
