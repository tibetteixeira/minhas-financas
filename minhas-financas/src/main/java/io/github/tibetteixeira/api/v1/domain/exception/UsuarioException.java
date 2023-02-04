package io.github.tibetteixeira.api.v1.domain.exception;

public class UsuarioException extends RuntimeException {

    public UsuarioException() {
        super();
    }

    public UsuarioException(String message) {
        super(message);
    }
}