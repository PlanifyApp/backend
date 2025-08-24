package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {
    boolean existsByWalletId(Long walletId);
}
