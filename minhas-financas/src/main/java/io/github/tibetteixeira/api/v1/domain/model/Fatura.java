package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
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
    private Date dataVencimento;

    @Column(name = "status_pagamento_fatura")
    private StatusPagamentoFatura status;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;

    public FaturaDTO toDTO() {
        FaturaDTO fatura = new FaturaDTO();

        fatura.setId(id);
        fatura.setDataVencimento(dataVencimento);
        fatura.setStatus(status);
        fatura.setValorPago(valorPago);
        fatura.setCartao(cartao.toDTO());

        return fatura;
    }
}
