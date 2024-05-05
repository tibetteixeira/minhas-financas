package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.model.dto.GastoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.tibetteixeira.api.util.CollectionsUtils.listaValida;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "fatura")
public class Fatura implements Serializable {

    @Id
    @GeneratedValue(generator = "fatura_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "fatura_id_seq", sequenceName = "fatura_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "status_pagamento_fatura")
    @Enumerated(EnumType.STRING)
    private StatusPagamentoFatura status = StatusPagamentoFatura.ABERTO;

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
        List<GastoDTO> gastosDTO = null;

        if (listaValida(this.gastos))
            gastosDTO = this.gastos.stream()
                    .map(Gasto::toDTO)
                    .toList();

        return FaturaDTO.builder()
                .id(id)
                .dataVencimento(dataVencimento)
                .status(status)
                .gastos(gastosDTO)
                .valorPago(valorPago)
                .cartaoId(cartao.getId())
                .build();
    }
}
