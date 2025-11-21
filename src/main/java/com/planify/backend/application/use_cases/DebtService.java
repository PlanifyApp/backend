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

    // Obtener deudas de un usuario + remainingDebt
    public Flux<DebtResponseDTO> getDebtsByUserId(Long userId) {
        return debtRepository.findAllByUserId(userId)
                .map(this::toResponse);
    }

    // NUEVO: Suma total de deuda actual
    public Mono<Integer> getTotalDebtByUserId(Long userId) {
        return debtRepository.findAllByUserId(userId)
                .map(DebtEntity::getCurrentDebt)
                .reduce(0, Integer::sum);
    }

    // UPDATE corregido (no suma, solo reemplaza)
    public Mono<DebtResponseDTO> updateDebt(Long id, UpdateDebtDTO dto) {
        return debtRepository.findById(id)
                .flatMap(existing -> {

                    // Solo actualiza si llega un valor
                    if (dto.getName() != null) {
                        existing.setName(dto.getName());
                    }

                    if (dto.getCurrentDebt() != null) {
                        existing.setCurrentDebt(dto.getCurrentDebt());
                    }

                    // principalAmount se suma SOLO si viene en el DTO
                    if (dto.getPrincipalAmount() != null) {
                        existing.setPrincipalAmount(
                                existing.getPrincipalAmount() + dto.getPrincipalAmount()
                        );
                    }

                    if (dto.getMinimumPayment() != null) {
                        existing.setMinimumPayment(dto.getMinimumPayment());
                    }

                    if (dto.getDueDate() != null) {
                        existing.setDueDate(dto.getDueDate());
                    }

                    if (dto.getIcon() != null) {
                        existing.setIcon(dto.getIcon());
                    }

                    return debtRepository.save(existing);
                })
                .map(this::toResponse);
    }

    public Mono<Void> deleteDebt(Long id) {
        return debtRepository.deleteById(id);
    }

    // DTO builder con remainingDebt
    private DebtResponseDTO toResponse(DebtEntity e) {
        int remaining = e.getCurrentDebt() - e.getPrincipalAmount();

        return new DebtResponseDTO(
                e.getId(),
                e.getUserId(),
                e.getName(),
                e.getCurrentDebt(),
                e.getPrincipalAmount(),
                e.getMinimumPayment(),
                e.getDueDate(),
                e.getIcon(),
                remaining
        );
    }
}
