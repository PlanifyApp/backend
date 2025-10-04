package com.planify.backend.infrastructure.security.firebase;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.application.use_cases.GoogleAuthUseCase;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GoogleAuthService implements GoogleAuthUseCase {

    private final ValidateFirebaseTokenUseCase validateFirebaseTokenUseCase;
    private final UsersRepository usersRepository;

    public GoogleAuthService(ValidateFirebaseTokenUseCase validateFirebaseTokenUseCase,
                             UsersRepository usersRepository) {
        this.validateFirebaseTokenUseCase = validateFirebaseTokenUseCase;
        this.usersRepository = usersRepository;
    }

    @Override
    public Mono<FirebaseUser> execute(String idToken) throws FirebaseAuthException {
        return validateFirebaseTokenUseCase.execute(idToken)
                .flatMap(firebaseUser ->
                        usersRepository.findByGoogleId(firebaseUser.uid())
                                .switchIfEmpty(usersRepository.save(
                                        UsersEntity.builder()
                                                .googleId(firebaseUser.uid())
                                                .email(firebaseUser.email())
                                                .build()
                                ))
                                .thenReturn(firebaseUser)
                );
    }
}
