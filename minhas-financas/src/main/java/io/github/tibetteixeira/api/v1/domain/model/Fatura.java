package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fatura")
public class Fatura {

    @Id
    @GeneratedValue(generator = "fatura_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "fatura_id_seq", sequenceName = "fatura_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_vencimento")
    @NonNull
    private Date dataVencimento;

    @Column(name = "status_pagamento_fatura")
    private StatusPagamentoFatura status;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    @NonNull
    private Cartao cartao;
}
