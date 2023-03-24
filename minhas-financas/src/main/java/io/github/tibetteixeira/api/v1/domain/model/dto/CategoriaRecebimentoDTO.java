package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaRecebimentoDTO {

    private Integer id;
    private String descricao;

    public CategoriaRecebimento toModel() {
        CategoriaRecebimento categoriaRecebimento = new CategoriaRecebimento();

        categoriaRecebimento.setId(id);
        categoriaRecebimento.setDescricao(descricao);

        return categoriaRecebimento;
    }
}
