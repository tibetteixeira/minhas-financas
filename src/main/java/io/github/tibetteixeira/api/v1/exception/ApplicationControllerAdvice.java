package io.github.tibetteixeira.api.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    private static final String ERRO_LOCAL_DATE_TIME =  "Failed to deserialize java.time.LocalDateTime";
    private static final String ERRO_VALOR_ENUM_INVALIDO =  "not one of the values accepted for Enum class";

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

    @ExceptionHandler(GastoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleGastoException(GastoException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(CartaoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleCartaoException(CartaoException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(CaixaEconomiaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleCaixaEconomiaException(CaixaEconomiaException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(ItemCaixaEconomiaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleItemCaixaEconomiaException(ItemCaixaEconomiaException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(FaturaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleFaturaException(FaturaException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(RecebimentoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRecebimentoException(RecebimentoException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(PagamentoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePagamentoException(PagamentoException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getMessage().contains(ERRO_LOCAL_DATE_TIME))
            return new ApiErrors(FORMATO_DATA_INVALIDA);

        if (e.getMessage().contains(ERRO_VALOR_ENUM_INVALIDO))
            return new ApiErrors(VALOR_ENUM_INVALIDO);

        return new ApiErrors(FORMATO_JSON_INVALIDO);
    }


}
