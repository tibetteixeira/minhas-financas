package io.github.tibetteixeira.api.v1.domain.model;


import io.github.tibetteixeira.api.v1.domain.model.dto.ItemCaixaEconomiaDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_caixa_economia")
public class ItemCaixaEconomia {

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
        ItemCaixaEconomiaDTO item = new ItemCaixaEconomiaDTO();

        item.setId(id);
        item.setValor(valor);
        item.setDataEconomia(dataEconomia);
        item.setCaixa(caixa.toDTO());

        return item;
    }
}
