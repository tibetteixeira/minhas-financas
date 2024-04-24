package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaGastoDTO {

    private Integer id;
    private String descricao;

    public CategoriaGasto toModel() {
        return CategoriaGasto.builder()
                .id(id)
                .descricao(descricao)
                .build();
    }
}
