package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCaixaEconomiaDTO {

    private Integer id;
    private BigDecimal valor;
    private Date dataEconomia;
    private CaixaEconomiaDTO caixa;

    public ItemCaixaEconomia toModel() {
        ItemCaixaEconomia item = new ItemCaixaEconomia();

        item.setId(id);
        item.setValor(valor);
        item.setDataEconomia(dataEconomia);
        item.setCaixa(caixa.toModel());

        return item;
    }
}
