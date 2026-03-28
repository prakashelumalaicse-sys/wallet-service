package com.prakash.wallet.dto;

import java.util.UUID;

public record WalletResponse(
	UUID id,
	double balance
) {
}
