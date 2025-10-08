package com.planify.backend.presentation.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.planify.backend.infrastructure.security.firebase.FirebaseTokenValidator;

import reactor.core.publisher.Mono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirebaseServiceTest {

    @Mock
    private FirebaseAuth firebaseAuth;

    @InjectMocks
    private FirebaseTokenValidator firebaseTokenValidator;

    @BeforeEach
    void setUp() {
        firebaseTokenValidator = new FirebaseTokenValidator(firebaseAuth);
    }

    @Test
    void testValidarToken() throws Exception {
        String fakeToken = "token_falso";

        FirebaseToken mockDecoded = mock(FirebaseToken.class);
        when(mockDecoded.getUid()).thenReturn("uid_prueba");

        UserRecord mockUser = mock(UserRecord.class);

        when(firebaseAuth.verifyIdToken(fakeToken)).thenReturn(mockDecoded);
        when(firebaseAuth.getUser("uid_prueba")).thenReturn(mockUser);

        Mono<FirebaseUser> result = firebaseTokenValidator.execute(fakeToken);

        assertNotNull(result);
        verify(firebaseAuth).verifyIdToken(fakeToken);
        verify(firebaseAuth).getUser(eq("uid_prueba"));
    }

}