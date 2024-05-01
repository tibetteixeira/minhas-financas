package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.CartaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class ValidadorCartao {

    public void validar(Integer id) {
        if (isNull(id))
            throw new CartaoException(ID_DO_CARTAO_NAO_PODE_SER_VAZIO);
    }

    public void validar(Cartao cartao) {
        if (isNull(cartao))
            throw new CartaoException(CARTAO_NAO_PODE_SER_VAZIO);

        if (isEmpty(cartao.getUltimosQuatroDigitosCartao()))
            throw new CartaoException(ULTIMOS_QUATRO_DIGITOS_NAO_PODE_SER_VAZIO);

        validarNome(cartao.getNome());
        validarDiaVencimento(cartao.getDiaVencimento());
    }

    public void validar(Cartao cartao, Integer id) {
        validar(id);
        validar(cartao);

        if (isFalse(id.equals(cartao.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);
    }

    public void validarNome(String nome) {
        if (isEmpty(nome))
            throw new CartaoException(NOME_NAO_PODE_SER_VAZIO);
    }

    public void validarDiaVencimento(Integer diaVencimento) {
        if (isNull(diaVencimento))
            throw new CartaoException(DIA_VENCIMENTO_NAO_PODE_SER_VAZIO);

        if (diaVencimento < 1 || diaVencimento > 31)
            throw new CartaoException(DIA_VENCIMENTO_NAO_PODE_SER_INVALIDO);
    }
}
