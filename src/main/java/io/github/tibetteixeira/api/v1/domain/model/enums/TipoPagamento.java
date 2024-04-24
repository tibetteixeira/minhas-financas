package io.github.tibetteixeira.api.v1.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPagamento {

    CARTAO("Cartão"),
    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    OUTRO("Outro");

    private final String descricao;

}
