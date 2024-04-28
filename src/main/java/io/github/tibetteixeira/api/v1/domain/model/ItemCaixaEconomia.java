package io.github.tibetteixeira.api.v1.domain.model;


import io.github.tibetteixeira.api.v1.domain.model.dto.ItemCaixaEconomiaDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "item_caixa_economia")
public class ItemCaixaEconomia implements Serializable {

    @Id
    @GeneratedValue(generator = "item_caixa_economia_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "item_caixa_economia_id_seq", sequenceName = "item_caixa_economia_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column
    private BigDecimal valor;

    @Column(name = "data_economia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEconomia;

    @ManyToOne
    @JoinColumn(name = "id_caixa_economia")
    private CaixaEconomia caixa;

    public ItemCaixaEconomiaDTO toDTO() {
        return ItemCaixaEconomiaDTO.builder()
                .id(id)
                .valor(valor)
                .dataEconomia(dataEconomia)
                .caixaId(Objects.nonNull(caixa) ? caixa.getId() : null)
                .build();
    }
}
