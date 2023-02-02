package io.github.tibetteixeira.api.v1.domain.model;


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

    @Column
    private Integer prazo;

    @Column(name = "data_caixa_economia")
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_caixa_economia")
    @NonNull
    private CaixaEconomia caixa;
}
