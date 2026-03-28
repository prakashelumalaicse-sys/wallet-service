package com.prakash.wallet.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleWalletNotFound(
		WalletNotFoundException exception,
		HttpServletRequest request
	) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidInput(
		InvalidAmountException exception,
		HttpServletRequest request
	) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ApiErrorResponse> handleInsufficientFunds(
		InsufficientBalanceException exception,
		HttpServletRequest request
	) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI());
	}

	private ResponseEntity<ApiErrorResponse> buildErrorResponse(
		HttpStatus status,
		String message,
		String path
	) {
		ApiErrorResponse response = new ApiErrorResponse(
			Instant.now(),
			status.value(),
			status.getReasonPhrase(),
			message,
			path
		);
		return ResponseEntity.status(status).body(response);
	}
}
