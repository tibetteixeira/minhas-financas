package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaGastoDTO {

    private Integer id;
    private String descricao;

    public CategoriaGasto toModel() {
        CategoriaGasto categoriaGasto = new CategoriaGasto();

        categoriaGasto.setId(id);
        categoriaGasto.setDescricao(descricao);

        return categoriaGasto;
    }
}
