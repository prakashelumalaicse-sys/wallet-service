package com.prakash.wallet.service;

import java.util.UUID;

public interface WalletService {

	double deposit(UUID walletId, double amount);

	double withdraw(UUID walletId, double amount);

	double getBalance(UUID walletId);
}
