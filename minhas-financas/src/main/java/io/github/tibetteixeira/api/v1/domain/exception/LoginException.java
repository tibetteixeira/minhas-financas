package io.github.tibetteixeira.api.v1.domain.exception;

public class LoginException extends RuntimeException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
