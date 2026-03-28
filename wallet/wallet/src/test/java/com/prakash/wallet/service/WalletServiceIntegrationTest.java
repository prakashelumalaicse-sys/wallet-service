package com.prakash.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prakash.wallet.entity.Wallet;
import com.prakash.wallet.exception.InsufficientBalanceException;
import com.prakash.wallet.exception.WalletNotFoundException;
import com.prakash.wallet.repository.WalletRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceIntegrationTest {

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletRepository walletRepository;

	@BeforeEach
	void setUp() {
		walletRepository.deleteAll();
	}

	@Test
	void depositShouldPersistUpdatedBalance() {
		Wallet wallet = walletRepository.save(Wallet.builder().balance(100.0).build());

		double updatedBalance = walletService.deposit(wallet.getId(), 25.0);

		assertEquals(125.0, updatedBalance);
		assertEquals(125.0, walletRepository.findById(wallet.getId()).orElseThrow().getBalance());
	}

	@Test
	void withdrawShouldPersistUpdatedBalance() {
		Wallet wallet = walletRepository.save(Wallet.builder().balance(100.0).build());

		double updatedBalance = walletService.withdraw(wallet.getId(), 45.0);

		assertEquals(55.0, updatedBalance);
		assertEquals(55.0, walletRepository.findById(wallet.getId()).orElseThrow().getBalance());
	}

	@Test
	void withdrawShouldThrowInsufficientBalanceException() {
		Wallet wallet = walletRepository.save(Wallet.builder().balance(10.0).build());

		assertThrows(InsufficientBalanceException.class, () -> walletService.withdraw(wallet.getId(), 50.0));
	}

	@Test
	void getBalanceShouldThrowWalletNotFoundException() {
		assertThrows(WalletNotFoundException.class, () -> walletService.getBalance(UUID.randomUUID()));
	}
}
