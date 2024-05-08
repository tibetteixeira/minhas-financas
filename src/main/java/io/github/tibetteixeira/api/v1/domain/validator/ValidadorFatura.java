package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.exception.FaturaException;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.DATA_NAO_PODE_SER_VAZIA;
import static java.util.Objects.isNull;

@Component
public class ValidadorFatura {

    public void validar(Integer id) {
        if (isNull(id))
            throw new FaturaException(ID_NAO_PODE_SER_VAZIO);
    }

    public void validar(Cartao cartao, Integer ano, Integer mes) {
        validarCartao(cartao);
        validarAno(ano);
        validarMes(mes);
    }

    public void validarCartao(Cartao cartao) {
        if (isNull(cartao) || isNull(cartao.getId()))
            throw new FaturaException(CARTAO_NAO_PODE_SER_VAZIO);
    }

    public void validarAno(Integer ano) {
        if (isNull(ano))
            throw new FaturaException(DATA_NAO_PODE_SER_VAZIA);

        if (ano < 2010)
            throw new FaturaException(DATA_NAO_PODE_SER_ANTERIOR_A_2010);
    }

    public void validarMes(Integer mes) {
        if (isNull(mes))
            throw new FaturaException(DATA_NAO_PODE_SER_VAZIA);

        if (1 > mes || mes > 12)
            throw new FaturaException(MES_NAO_PODE_SER_INVALIDO);
    }
}
