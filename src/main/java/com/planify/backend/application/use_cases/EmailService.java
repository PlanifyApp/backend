package com.planify.backend.application.use_cases;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public Mono<Void> sendPasswordResetEmail(String to, String resetLink) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("planifyoficial@gmail.com");
            message.setTo(to);
            message.setSubject("Restablecer contraseña - Planify");
            message.setText("Hola,\n\n" +
                    "Recibimos una solicitud para restablecer tu contraseña.\n" +
                    "Haz clic en el siguiente enlace para actualizar tu contraseña:\n" +
                    resetLink + "\n\n" +
                    "Si no solicitaste esto, ignora este correo.\n\n" +
                    "Saludos,\nPlanify Team");
            mailSender.send(message);
        });
    }
}
