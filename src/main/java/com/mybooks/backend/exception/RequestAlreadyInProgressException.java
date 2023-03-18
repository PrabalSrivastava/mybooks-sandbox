package com.mybooks.backend.exception;

public class RequestAlreadyInProgressException extends RuntimeException {
    public RequestAlreadyInProgressException(String message) {
        super(message);
    }
}
