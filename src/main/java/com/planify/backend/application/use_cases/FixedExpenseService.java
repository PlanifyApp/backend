package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.CreateFixedExpenseDTO;
import com.planify.backend.application.dtos.FixedExpenseDTO;
import com.planify.backend.domain.models.FixedExpenseEntity;
import com.planify.backend.infrastructure.repositories.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FixedExpenseService {

    private final FixedExpenseRepository repository;

    public Flux<FixedExpenseDTO> findAll() {
        return repository.findAll().map(this::toDTO);
    }

    public Mono<FixedExpenseDTO> findById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Mono<FixedExpenseDTO> save(FixedExpenseDTO dto) {
        return repository.save(toEntity(dto)).map(this::toDTO);
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    // mapper helpers
    private FixedExpenseDTO toDTO(FixedExpenseEntity e) {
        FixedExpenseDTO dto = new FixedExpenseDTO();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setAccountId(e.getAccountId());
        dto.setCategoryId(e.getCategoryId());
        dto.setName(e.getName());
        dto.setDateTime(e.getDateTime());
        dto.setBudget(e.getBudget());
        dto.setCurrentValue(e.getCurrentValue());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    // Para crear (no tiene ID todavía)
    private FixedExpenseEntity toEntity(CreateFixedExpenseDTO dto) {
        FixedExpenseEntity e = new FixedExpenseEntity();
        e.setUserId(dto.getUserId());
        e.setAccountId(dto.getAccountId());
        e.setCategoryId(dto.getCategoryId());
        e.setName(dto.getName());
        e.setDateTime(dto.getDateTime());
        e.setBudget(dto.getBudget());
        e.setCurrentValue(dto.getCurrentValue());
        e.setCreatedAt(LocalDateTime.now());
        return e;
    }

    // Para guardar/actualizar desde DTO completo
    private FixedExpenseEntity toEntity(FixedExpenseDTO dto) {
        FixedExpenseEntity e = new FixedExpenseEntity();
        e.setId(dto.getId()); // aquí sí existe el id
        e.setUserId(dto.getUserId());
        e.setAccountId(dto.getAccountId());
        e.setCategoryId(dto.getCategoryId());
        e.setName(dto.getName());
        e.setDateTime(dto.getDateTime());
        e.setBudget(dto.getBudget());
        e.setCurrentValue(dto.getCurrentValue());
        e.setCreatedAt(dto.getCreatedAt());
        return e;
    }

    // CRUD reactivo
    public Mono<FixedExpenseDTO> create(CreateFixedExpenseDTO dto) {
        FixedExpenseEntity entity = toEntity(dto);
        return repository.save(entity).map(this::toDTO);
    }

    public Flux<FixedExpenseDTO> findByUser(Integer userId) {
        return repository.findByUserId(userId).map(this::toDTO);
    }

    public Mono<FixedExpenseDTO> update(Long id, CreateFixedExpenseDTO dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(dto.getName());
                    existing.setDateTime(dto.getDateTime());
                    existing.setBudget(dto.getBudget());
                    existing.setCurrentValue(dto.getCurrentValue());
                    existing.setAccountId(dto.getAccountId());
                    existing.setCategoryId(dto.getCategoryId());
                    return repository.save(existing);
                })
                .map(this::toDTO);
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
