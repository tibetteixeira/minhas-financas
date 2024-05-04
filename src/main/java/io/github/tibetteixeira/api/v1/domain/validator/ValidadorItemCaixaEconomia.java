package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.ItemCaixaEconomiaException;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.util.NumericUtils.igualA;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

@Component
public class ValidadorItemCaixaEconomia {

    public void validar(ItemCaixaEconomia itemCaixaEconomia) {
        if (isNull(itemCaixaEconomia))
            throw new ItemCaixaEconomiaException(ITEM_CAIXA_NAO_PODE_SER_VAZIO);

        if (isNull(itemCaixaEconomia.getValor()) || igualA(itemCaixaEconomia.getValor(), ZERO))
            throw new ItemCaixaEconomiaException(ITEM_CAIXA_NAO_PODE_SER_VAZIO);

        validarCaixa(itemCaixaEconomia.getCaixa());
    }

    public void validarId(Integer id) {
        if (isNull(id))
            throw new ItemCaixaEconomiaException(ID_ITEM_CAIXA_NAO_PODE_SER_VAZIO);
    }

    public void validarCaixa(Integer caixaId) {
        if (isNull(caixaId))
            throw new ItemCaixaEconomiaException(ID_CAIXA_NAO_PODE_SER_VAZIO);
    }

    public void validarCaixa(CaixaEconomia caixa) {
        if (isNull(caixa))
            throw new ItemCaixaEconomiaException(ID_CAIXA_NAO_PODE_SER_VAZIO);

        validarCaixa(caixa.getId());
    }

}
