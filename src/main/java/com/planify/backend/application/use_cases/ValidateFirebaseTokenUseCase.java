package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.FirebaseUser;

public interface ValidateFirebaseTokenUseCase {

    public FirebaseUser execute(String idToken);

}
