package com.planify.backend.application.use_cases;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleAuthUseCaseImpl implements GoogleAuthUseCase {

    // ⚠️ Reemplaza este clientId por el de tu proyecto en Google Cloud Console
    private static final String CLIENT_ID = "TU_CLIENT_ID.apps.googleusercontent.com";

    @Override
    public UsersEntity authenticate(GoogleLoginRequestDTO request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory()
            ).setAudience(Collections.singletonList(CLIENT_ID)).build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());

            if (idToken == null) {
                throw new RuntimeException("Token de Google inválido");
            }

            Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String firstname = (String) payload.get("firstname");
            String lastname = (String) payload.get("lastname");
            String pictureUrl = (String) payload.get("picture");

            // Aquí puedes buscar o crear el usuario en tu BD
            UsersEntity user = new UsersEntity();
            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setProfilePicture(pictureUrl);

            return user;

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar token de Google", e);
        }
    }
}
