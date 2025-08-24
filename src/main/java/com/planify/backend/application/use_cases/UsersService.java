package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.domain.models.AuthMethodEntity;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodRepository;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final AuthMethodRepository authMethodRepository;

    public UsersService(UsersRepository usersRepository, AuthMethodRepository authMethodRepository) {
        this.usersRepository = usersRepository;
        this.authMethodRepository = authMethodRepository;
    }

    public UsersEntity createUser(RegisterUserDTO dto) {
        UsersEntity user = UsersEntity.builder()
                .email(dto.getEmail())
                .gender(UsersEntity.GenderEnum.other)
                .build();
        UsersEntity savedUser = usersRepository.save(user);

        AuthMethodEntity authMethod = AuthMethodEntity.builder()
                .user_id(savedUser.getId())
                .provider("local")
                .provider_user_id(String.valueOf(savedUser.getId()))
                .password(dto.getPassword())
                .build();
        authMethodRepository.save(authMethod);

        return savedUser;
    }

    public List<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }
}
