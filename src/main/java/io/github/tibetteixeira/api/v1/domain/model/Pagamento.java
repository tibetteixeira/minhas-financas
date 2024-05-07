package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.PagamentoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "pagamento")
public class Pagamento implements Serializable {

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
    private LocalDateTime dataPagamento;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

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
                .formaPagamento(formaPagamento)
                .faturaId(Objects.nonNull(fatura) ? fatura.getId() : null)
                .usuarioId(usuario.getId())
                .build();
    }

}
