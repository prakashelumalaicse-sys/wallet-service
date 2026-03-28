package com.prakash.wallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prakash.wallet.entity.Wallet;
import com.prakash.wallet.exception.InsufficientBalanceException;
import com.prakash.wallet.exception.WalletNotFoundException;
import com.prakash.wallet.repository.WalletRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

	@Mock
	private WalletRepository walletRepository;

	@InjectMocks
	private WalletServiceImpl walletService;

	@Test
	void depositShouldIncreaseBalanceWhenWalletExists() {
		UUID walletId = UUID.randomUUID();
		Wallet wallet = Wallet.builder()
			.id(walletId)
			.balance(100.0)
			.build();

		when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

		double updatedBalance = walletService.deposit(walletId, 50.0);

		assertEquals(150.0, updatedBalance);
		assertEquals(150.0, wallet.getBalance());
		verify(walletRepository).findByIdForUpdate(walletId);
	}

	@Test
	void withdrawShouldDecreaseBalanceWhenFundsAreAvailable() {
		UUID walletId = UUID.randomUUID();
		Wallet wallet = Wallet.builder()
			.id(walletId)
			.balance(100.0)
			.build();

		when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

		double updatedBalance = walletService.withdraw(walletId, 40.0);

		assertEquals(60.0, updatedBalance);
		assertEquals(60.0, wallet.getBalance());
		verify(walletRepository).findByIdForUpdate(walletId);
	}

	@Test
	void withdrawShouldThrowWhenBalanceIsInsufficient() {
		UUID walletId = UUID.randomUUID();
		Wallet wallet = Wallet.builder()
			.id(walletId)
			.balance(25.0)
			.build();

		when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

		assertThrows(InsufficientBalanceException.class, () -> walletService.withdraw(walletId, 30.0));
		assertEquals(25.0, wallet.getBalance());
		verify(walletRepository).findByIdForUpdate(walletId);
	}

	@Test
	void getBalanceShouldThrowWhenWalletDoesNotExist() {
		UUID walletId = UUID.randomUUID();

		when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

		assertThrows(WalletNotFoundException.class, () -> walletService.getBalance(walletId));
		verify(walletRepository).findById(walletId);
	}
}
