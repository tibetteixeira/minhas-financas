package io.github.tibetteixeira.api.v1.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormaPagamento {

    CREDITO("Cartão de Crédito"),
    DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    OUTRO("Outro");

    private final String descricao;

}
