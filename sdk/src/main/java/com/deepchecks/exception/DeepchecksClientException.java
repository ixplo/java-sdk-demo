package com.deepchecks.exception;

public class DeepchecksClientException extends RuntimeException {
    public DeepchecksClientException() {
    }

    public DeepchecksClientException(String message) {
        super(message);
    }

    public DeepchecksClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
