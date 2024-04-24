package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "fatura")
public class Fatura {

    @Id
    @GeneratedValue(generator = "fatura_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "fatura_id_seq", sequenceName = "fatura_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @Column(name = "status_pagamento_fatura")
    private StatusPagamentoFatura status = StatusPagamentoFatura.ABERTO;

    @Column
    @Enumerated(EnumType.STRING)
    private Mes mes;

    @Column
    private Integer ano;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;

    @OneToMany(mappedBy = "fatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gasto> gastos;

    public Fatura(Integer id) {
        this.id = id;
    }

    public FaturaDTO toDTO() {
        return FaturaDTO.builder()
                .id(id)
                .dataVencimento(dataVencimento)
                .status(status)
                .valorPago(valorPago)
                .cartaoId(cartao.getId())
                .mes(mes)
                .ano(ano)
                .build();
    }
}
