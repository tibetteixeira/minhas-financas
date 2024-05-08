package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.util.NumericUtils;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.GastoException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento.CREDITO;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.math.BigDecimal.ZERO;
import static java.time.LocalDateTime.of;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class ValidadorGasto {

    public void validar(Gasto gasto) {
        if (isNull(gasto))
            throw new GastoException(GASTO_NAO_PODE_SER_VAZIO);

        validarDescricao(gasto.getDescricao());
        validarValor(gasto.getValor());
        validarData(gasto.getDataGasto());
        validarFormaPagamento(gasto.getFormaPagamento());
        validarCategoriaGasto(gasto.getCategoria());

        if (CREDITO.equals(gasto.getFormaPagamento()) && isFalse(cartaoValido(gasto.getCartao())))
            throw new GastoException(GASTO_POR_CREDITO_DEVE_TER_CARTAO);
    }

    private boolean cartaoValido(Cartao cartao) {
        return nonNull(cartao) && nonNull(cartao.getId());
    }

    public void validar(Integer id) {
        if (isNull(id))
            throw new GastoException(ID_NAO_PODE_SER_VAZIO);
    }

    public void validar(Integer id, Gasto gasto) {
        validar(id);
        validar(gasto);

        if (isFalse(id.equals(gasto.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);
    }

    public void validarDescricao(String descricacao) {
        if (isEmpty(descricacao))
            throw new GastoException(DESCRICAO_NAO_PODE_SER_VAZIA);
    }

    public void validarValor(BigDecimal valor) {
        if (isNull(valor))
            throw new GastoException(VALOR_NAO_PODE_SER_VAZIO);

        if (NumericUtils.menorQueOuIgualA(valor, ZERO))
            throw new GastoException(VALOR_NAO_PODE_SER_ZERO_OU_NEGATIVO);
    }

    public void validarData(LocalDateTime data) {
        if (isNull(data))
            throw new GastoException(DATA_NAO_PODE_SER_VAZIA);

        if (data.isBefore(of(2010, 1, 1, 0, 0)))
            throw new GastoException(DATA_NAO_PODE_SER_ANTERIOR_A_2010);
    }

    public void validarFormaPagamento(FormaPagamento forma) {
        if (isNull(forma))
            throw new GastoException(FORMA_PAGAMENTO_NAO_PODE_SER_VAZIA);
    }

    public void validarCategoriaGasto(CategoriaGasto categoria) {
        if (isNull(categoria))
            throw new GastoException(CATEGORIA_NAO_PODE_SER_VAZIA);
    }

    public void validarAno(Integer ano) {
        if (isNull(ano))
            throw new GastoException(DATA_NAO_PODE_SER_VAZIA);

        if (ano < 2010)
            throw new GastoException(DATA_NAO_PODE_SER_ANTERIOR_A_2010);
    }

    public void validarMes(Mes mes) {
        if (isNull(mes))
            throw new GastoException(DATA_NAO_PODE_SER_VAZIA);
    }

}
