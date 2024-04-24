package io.github.tibetteixeira.api.v1.exception;

public class NotSameIdException extends RuntimeException {

    public NotSameIdException() {
        super();
    }

    public NotSameIdException(String message) {
        super(message);
    }
}
