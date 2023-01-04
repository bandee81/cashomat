package com.example.cashomat.exception;

public class MoneyStorageException extends RuntimeException {
    public MoneyStorageException() {
    }

    public MoneyStorageException(String message) {
        super(message);
    }
}
