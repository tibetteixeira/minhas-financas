package io.github.tibetteixeira.api.v1.domain.exception;

public class CartaoException extends RuntimeException {

    public CartaoException() {
        super();
    }

    public CartaoException(String message) {
        super(message);
    }
}
