package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCaixaEconomiaDTO {

    private Integer id;
    private BigDecimal valor;
    private Date dataEconomia;
    private Integer caixaId;

    public ItemCaixaEconomia toModel() {
        return ItemCaixaEconomia.builder()
                .id(id)
                .valor(valor)
                .dataEconomia(dataEconomia)
                .caixa(new CaixaEconomia(caixaId))
                .build();
    }
}
