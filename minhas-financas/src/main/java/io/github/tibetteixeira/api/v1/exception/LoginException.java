package io.github.tibetteixeira.api.v1.exception;

public class LoginException extends MinhasFinancasException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
