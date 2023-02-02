package io.github.tibetteixeira.api.v1.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPagamentoFatura {

    PAGO("Pago"),
    PAGO_PARCIALMENTE("Pago parcialmente"),
    ATRASADO("Atrasado");

    private final String descricao;
}
