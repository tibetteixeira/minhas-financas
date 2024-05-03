package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.CaixaEconomiaException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class ValidadorCaixaEconomia {

    public void validar(CaixaEconomia caixa) {
        if (isNull(caixa))
            throw new CaixaEconomiaException(CAIXA_NAO_PODE_SER_VAZIO);

        validarNome(caixa.getNome());
    }

    public void validar(Integer id, CaixaEconomia caixa) {
        validar(caixa);
        validarId(id);

        if (BooleanUtils.isFalse(id.equals(caixa.getId())))
            throw new NotSameIdException(ID_ROTA_DIFERENTE_ID_OBJETO);
    }

    public void validarId(Integer id) {
        if (isNull(id))
            throw new CaixaEconomiaException(ID_CAIXA_NAO_PODE_SER_VAZIO);
    }

    public void validarNome(String nome) {
        if (isEmpty(nome))
            throw new CaixaEconomiaException(NOME_CAIXA_NAO_PODE_SER_VAZIO);
    }

}
