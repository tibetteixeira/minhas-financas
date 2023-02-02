package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.enums.TipoPagamento;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "pagamento_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "pagamento_id_seq", sequenceName = "pagamento_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column
    @NonNull
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column(name = "data_pagamento")
    @NonNull
    private Date dataPagamento;

    @Column(name = "tipo_pagamento")
    @NonNull
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NonNull
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_gasto")
    private Gasto gasto;

    @ManyToOne
    @JoinColumn(name = "id_fatura")
    private Fatura fatura;

}
