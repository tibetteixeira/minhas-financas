package io.github.tibetteixeira.api.v1.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super();
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }
}
