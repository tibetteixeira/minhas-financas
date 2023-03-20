package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GastoDTO {

    private Integer id;
    private Date dataGasto;
    private BigDecimal valor;
    private String descricao;
    private CategoriaGastoDTO categoria;
    private FaturaDTO fatura;

    public Gasto toModel() {
        Gasto gasto = new Gasto();

        gasto.setId(id);
        gasto.setDataGasto(dataGasto);
        gasto.setValor(valor);
        gasto.setDescricao(descricao);
        gasto.setCategoria(categoria.toModel());
        gasto.setFatura(fatura.toModel());

        return gasto;
    }
}
