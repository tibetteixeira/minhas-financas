package io.github.tibetteixeira.api.v1.domain.exception;

public class PagamentoException extends RuntimeException {

    public PagamentoException() {
        super();
    }

    public PagamentoException(String message) {
        super(message);
    }
}
