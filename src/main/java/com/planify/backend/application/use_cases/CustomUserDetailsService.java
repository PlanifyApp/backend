package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UsersRepository userRepository;
    private final AuthMethodsRepository authMethodsRepository;

    public CustomUserDetailsService(UsersRepository userRepository,
                                    AuthMethodsRepository authMethodsRepository) {
        this.userRepository = userRepository;
        this.authMethodsRepository = authMethodsRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuario no encontrado")))
                .flatMap(user ->
                        authMethodsRepository.findByUserIdAndProvider(user.getId().intValue(), "local")
                                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Método de autenticación no encontrado")))
                                .map(auth -> buildUserDetails(user, auth.getPassword()))
                );
    }

    private UserDetails buildUserDetails(UsersEntity user, String encodedPassword) {
        return User.withUsername(user.getEmail())
                .password(encodedPassword)
                .authorities("ROLE_" + (user.getRole() != null ? user.getRole().toUpperCase() : "USER"))
                .build();
    }
}
