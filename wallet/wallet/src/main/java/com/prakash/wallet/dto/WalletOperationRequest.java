package com.prakash.wallet.dto;

import java.util.UUID;

public record WalletOperationRequest(
	UUID walletId,
	OperationType operationType,
	double amount
) {
	public enum OperationType {
		DEPOSIT,
		WITHDRAW
	}
}
