package io.github.tibetteixeira.api.v1.domain.exception;

public class FaturaException extends RuntimeException {

    public FaturaException() {
        super();
    }

    public FaturaException(String message) {
        super(message);
    }
}
