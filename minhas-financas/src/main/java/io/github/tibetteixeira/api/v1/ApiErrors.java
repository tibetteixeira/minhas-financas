package io.github.tibetteixeira.api.v1;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class ApiErrors {

    @Getter
    private final List<String> errors;

    public ApiErrors(String error) {
        errors = Collections.singletonList(error);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
