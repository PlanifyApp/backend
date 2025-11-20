package com.planify.backend.application.use_cases;
import com.planify.backend.application.dtos.CreateDebtDTO;
import com.planify.backend.application.dtos.DebtResponseDTO;
import com.planify.backend.application.dtos.UpdateDebtDTO;
import com.planify.backend.domain.models.DebtEntity;
import com.planify.backend.infrastructure.repositories.DebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DebtService {

    private final DebtRepository debtRepository;

    public Mono<DebtResponseDTO> createDebt(CreateDebtDTO dto) {
        DebtEntity entity = new DebtEntity(
                null,
                dto.getUserId(),
                dto.getName(),
                dto.getCurrentDebt(),
                dto.getPrincipalAmount(),
                dto.getMinimumPayment(),
                dto.getDueDate(),
                dto.getIcon()
        );

        return debtRepository.save(entity)
                .map(this::toResponse);
    }

    public Flux<DebtResponseDTO> getDebtsByUserId(Long userId) {
        return debtRepository.findAllByUserId(userId)
                .map(this::toResponse);
    }

    public Mono<DebtResponseDTO> updateDebt(Long id, UpdateDebtDTO dto) {
        return debtRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(dto.getName());
                    existing.setCurrentDebt(dto.getCurrentDebt());
                    existing.setPrincipalAmount(dto.getPrincipalAmount());
                    existing.setMinimumPayment(dto.getMinimumPayment());
                    existing.setDueDate(dto.getDueDate());
                    existing.setIcon(dto.getIcon());
                    return debtRepository.save(existing);
                })
                .map(this::toResponse);
    }

    public Mono<Void> deleteDebt(Long id) {
        return debtRepository.deleteById(id);
    }

    private DebtResponseDTO toResponse(DebtEntity e) {
        return new DebtResponseDTO(
                e.getId(),
                e.getUserId(),
                e.getName(),
                e.getCurrentDebt(),
                e.getPrincipalAmount(),
                e.getMinimumPayment(),
                e.getDueDate(),
                e.getIcon()
        );
    }
}
