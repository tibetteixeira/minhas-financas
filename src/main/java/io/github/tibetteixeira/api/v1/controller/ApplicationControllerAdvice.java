package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.ApiErrors;
import io.github.tibetteixeira.api.v1.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    /**
     * Trata as exceções do tipo NotFoundException retornando apenas as mensagens
     * @param e exceção do tipo NotFoundException lançada pelo sistema
     * @return ApiErrors com os erros tratados
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNotFoundException(NotFoundException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(MinhasFinancasException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleMinhasFinancasException(MinhasFinancasException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(NotSameIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleNotSameIdException(NotSameIdException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleUsuarioException(UsuarioException e) {
        return new ApiErrors(e.getMessage());
    }


}
