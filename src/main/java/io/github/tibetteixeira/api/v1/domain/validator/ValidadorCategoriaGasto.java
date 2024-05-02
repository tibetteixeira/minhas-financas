package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.exception.GastoException;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class ValidadorCategoriaGasto {

    public void validar(Integer id) {
        if (isNull(id))
            throw new GastoException(ID_CATEGORIA_GASTO_NAO_PODE_SER_VAZIO);
    }

    public void validarDescricao(String descricao) {
        if (isEmpty(descricao))
            throw new GastoException(DESCRICAO_NAO_PODE_SER_VAZIA);
    }
}
