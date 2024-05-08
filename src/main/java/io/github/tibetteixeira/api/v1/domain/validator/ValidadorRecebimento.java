package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.RecebimentoException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.util.NumericUtils.menorQueOuIgualA;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class ValidadorRecebimento {

    public void validar(Integer id) {
        if (isNull(id))
            throw new RecebimentoException(ID_NAO_PODE_SER_VAZIO);
    }

    public void validar(Recebimento recebimento) {
        if (isNull(recebimento))
            throw new RecebimentoException(RECEBIMENTO_NAO_PODE_SER_VAZIO);

        if (isNull(recebimento.getTipoRecebimento()))
            throw new RecebimentoException(TIPO_RECEBIMENTO_NAO_PODE_SER_VAZIO);

        if (isNull(recebimento.getValor()))
            throw new RecebimentoException(VALOR_NAO_PODE_SER_VAZIO);

        if (menorQueOuIgualA(recebimento.getValor(), ZERO))
            throw new RecebimentoException(VALOR_NAO_PODE_SER_ZERO_OU_NEGATIVO);

        validarDescricao(recebimento.getDescricao());
    }

    public void validar(Integer id, Recebimento recebimento) {
        validar(id);
        validar(recebimento);

        if (BooleanUtils.isFalse(id.equals(recebimento.getId())))
            throw new NotSameIdException(ID_ROTA_DIFERENTE_ID_OBJETO);
    }

    public void validarDescricao(String descricao) {
        if (isEmpty(descricao))
            throw new RecebimentoException(DESCRICAO_NAO_PODE_SER_VAZIA);
    }
}
