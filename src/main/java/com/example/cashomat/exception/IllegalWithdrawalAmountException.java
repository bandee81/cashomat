package com.example.cashomat.exception;

public class IllegalWithdrawalAmountException extends RuntimeException {
    public IllegalWithdrawalAmountException() {
    }

    public IllegalWithdrawalAmountException(String message) {
        super(message);
    }
}
