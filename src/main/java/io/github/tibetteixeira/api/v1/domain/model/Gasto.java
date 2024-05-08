package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.GastoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
@Table(name = "gasto")
public class Gasto implements Serializable {

    @Id
    @GeneratedValue(generator = "fatura_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "fatura_id_seq", sequenceName = "fatura_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_gasto")
    private LocalDateTime dataGasto;

    @Column
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @JoinColumn(name = "id_categoria")
    @ManyToOne
    private CategoriaGasto categoria;

    @ManyToOne
    @JoinColumn(name = "id_fatura")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Fatura fatura;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    @Transient
    private Cartao cartao;

    public GastoDTO toDTO() {
        return GastoDTO.builder()
                .id(id)
                .dataGasto(dataGasto)
                .valor(valor)
                .descricao(descricao)
                .formaPagamento(formaPagamento)
                .categoriaId(categoria.getId())
                .usuarioId(usuario.getId())
                .faturaId(Objects.nonNull(fatura) ? fatura.getId() : null)
                .build();
    }
}
