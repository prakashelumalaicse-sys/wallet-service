package com.prakash.wallet.dto;

import java.util.UUID;

public record WalletBalanceResponse(
	UUID walletId,
	double balance
) {
}
