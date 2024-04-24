package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.PagamentoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoPagamento;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "pagamento_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "pagamento_id_seq", sequenceName = "pagamento_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_fatura")
    private Fatura fatura;

    public PagamentoDTO toDTO() {
        return PagamentoDTO.builder()
                .id(id)
                .valor(valor)
                .descricao(descricao)
                .dataPagamento(dataPagamento)
                .tipoPagamento(tipoPagamento)
                .faturaId(Objects.nonNull(fatura) ? fatura.getId() : null)
                .usuarioId(usuario.getId())
                .build();
    }

}
