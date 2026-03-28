package com.prakash.wallet.repository;

import com.prakash.wallet.entity.Wallet;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select wallet from Wallet wallet where wallet.id = :id")
	Optional<Wallet> findByIdForUpdate(UUID id);
}
