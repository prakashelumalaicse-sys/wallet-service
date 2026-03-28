package com.prakash.wallet.service.impl;

import com.prakash.wallet.entity.Wallet;
import com.prakash.wallet.exception.InsufficientBalanceException;
import com.prakash.wallet.exception.InvalidAmountException;
import com.prakash.wallet.exception.WalletNotFoundException;
import com.prakash.wallet.repository.WalletRepository;
import com.prakash.wallet.service.WalletService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalletServiceImpl implements WalletService {

	private final WalletRepository walletRepository;

	@Override
	@Transactional
	public double deposit(UUID walletId, double amount) {
		validateWalletId(walletId);
		validateAmount(amount);
		log.info("Processing deposit for walletId={} amount={}", walletId, amount);
		Wallet wallet = getWalletForUpdate(walletId);
		wallet.setBalance(wallet.getBalance() + amount);
		log.info("Deposit successful for walletId={} newBalance={}", walletId, wallet.getBalance());
		return wallet.getBalance();
	}

	@Override
	@Transactional
	public double withdraw(UUID walletId, double amount) {
		validateWalletId(walletId);
		validateAmount(amount);
		log.info("Processing withdrawal for walletId={} amount={}", walletId, amount);
		Wallet wallet = getWalletForUpdate(walletId);
		if (wallet.getBalance() < amount) {
			log.warn("Withdrawal rejected for walletId={} currentBalance={} requestedAmount={}",
				walletId, wallet.getBalance(), amount);
			throw new InsufficientBalanceException("Insufficient balance for wallet: " + walletId);
		}
		wallet.setBalance(wallet.getBalance() - amount);
		log.info("Withdrawal successful for walletId={} newBalance={}", walletId, wallet.getBalance());
		return wallet.getBalance();
	}

	@Override
	public double getBalance(UUID walletId) {
		validateWalletId(walletId);
		log.info("Fetching balance for walletId={}", walletId);
		return walletRepository.findById(walletId)
			.map(Wallet::getBalance)
			.orElseThrow(() -> {
				log.warn("Wallet lookup failed for walletId={}", walletId);
				return new WalletNotFoundException("Wallet not found with id: " + walletId);
			});
	}

	private Wallet getWalletForUpdate(UUID walletId) {
		return walletRepository.findByIdForUpdate(walletId)
			.orElseThrow(() -> {
				log.warn("Wallet lookup failed for update walletId={}", walletId);
				return new WalletNotFoundException("Wallet not found with id: " + walletId);
			});
	}

	private void validateAmount(double amount) {
		if (amount <= 0) {
			throw new InvalidAmountException("Amount must be greater than zero");
		}
	}

	private void validateWalletId(UUID walletId) {
		if (walletId == null) {
			throw new InvalidAmountException("walletId must not be null");
		}
	}
}
