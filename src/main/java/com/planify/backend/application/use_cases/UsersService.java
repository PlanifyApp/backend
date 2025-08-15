package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersEntity createUser(UsersEntity user) {
        return usersRepository.save(user);
    }

    public List<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }
}
