package com.prakash.wallet.controller;

import com.prakash.wallet.dto.WalletBalanceResponse;
import com.prakash.wallet.dto.WalletOperationRequest;
import com.prakash.wallet.exception.InvalidAmountException;
import com.prakash.wallet.service.WalletService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	@PostMapping(path = "/api/v1/wallet")
	public WalletBalanceResponse updateWallet(@RequestBody WalletOperationRequest request) {
		validateRequest(request);
		log.info("Received wallet operation request walletId={} operationType={} amount={}",
			request.walletId(), request.operationType(), request.amount());
		double balance = switch (request.operationType()) {
			case DEPOSIT -> walletService.deposit(request.walletId(), request.amount());
			case WITHDRAW -> walletService.withdraw(request.walletId(), request.amount());
		};
		return new WalletBalanceResponse(request.walletId(), balance);
	}

	@GetMapping("/api/v1/wallets/{id}")
	public WalletBalanceResponse getBalance(@PathVariable UUID id) {
		if (id == null) {
			throw new InvalidAmountException("walletId must not be null");
		}
		log.info("Received balance lookup request walletId={}", id);
		double balance = walletService.getBalance(id);
		return new WalletBalanceResponse(id, balance);
	}

	private void validateRequest(WalletOperationRequest request) {
		if (request == null) {
			throw new InvalidAmountException("Request body must not be null");
		}
		if (request.walletId() == null) {
			throw new InvalidAmountException("walletId must not be null");
		}
		if (request.operationType() == null) {
			throw new InvalidAmountException("operationType must not be null");
		}
	}
}
