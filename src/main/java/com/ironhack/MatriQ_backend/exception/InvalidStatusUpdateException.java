package com.ironhack.MatriQ_backend.exception;

public class InvalidStatusUpdateException extends RuntimeException {
    public InvalidStatusUpdateException(String message) {
        super(message);
    }
}
