package io.github.tibetteixeira.api.v1.domain.model.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum TipoRecebimento {

    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    TED("Ted"),
    CHEQUE("Cheque"),
    OUTROS("Outros");

    private String descricao;
}
