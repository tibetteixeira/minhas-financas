package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RecebimentoDTO {

    private Integer id;
    private Date dataRecebimento;
    private BigDecimal valor;
    private String descricao;
    private CategoriaRecebimentoDTO categoria;
    private FaturaDTO fatura;

    public Recebimento toModel() {
        Recebimento recebimento = new Recebimento();

        recebimento.setId(id);
        recebimento.setDataRecebimento(dataRecebimento);
        recebimento.setValor(valor);
        recebimento.setDescricao(descricao);
        recebimento.setCategoria(categoria.toModel());

        return recebimento;
    }
}
