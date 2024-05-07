package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.PagamentoException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.util.NumericUtils.menorQueOuIgualA;
import static io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento.CREDITO;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

@Component
public class ValidadorPagamento {

    public void validar(Integer id) {
        if (isNull(id))
            throw new PagamentoException(ID_NAO_PODE_SER_VAZIO);
    }

    public void validar(Pagamento pagamento) {
        if (isNull(pagamento))
            throw new PagamentoException(PAGAMENTO_NAO_PODE_SER_VAZIO);

        if (isNull(pagamento.getFatura()) || isNull(pagamento.getFatura().getId()))
            throw new PagamentoException(FATURA_NAO_PODE_SER_VAZIA);

        if (isNull(pagamento.getFormaPagamento()))
            throw new PagamentoException(FORMA_PAGAMENTO_NAO_PODE_SER_VAZIA);

        if (CREDITO.equals(pagamento.getFormaPagamento()))
            throw new PagamentoException(FORMA_PAGAMENTO_INVALIDA);

        if (isNull(pagamento.getValor()))
            throw new PagamentoException(VALOR_NAO_PODE_SER_VAZIO);

        if (menorQueOuIgualA(pagamento.getValor(), ZERO))
            throw new PagamentoException(VALOR_NAO_PODE_SER_ZERO_OU_NEGATIVO);
    }

    public void validar(Integer id, Pagamento pagamento) {
        validar(id);
        validar(pagamento);

        if (BooleanUtils.isFalse(id.equals(pagamento.getId())))
            throw new NotSameIdException(ID_ROTA_DIFERENTE_ID_OBJETO);
    }
}
