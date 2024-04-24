package io.github.tibetteixeira.api.v1.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.CaixaEconomiaDTO;
import io.github.tibetteixeira.util.NumericUtils;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "caixa_economia")
public class CaixaEconomia {

    @Id
    @GeneratedValue(generator = "caixa_economia_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "caixa_economia_id_seq", sequenceName = "caixa_economia_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 100)
    private String nome;

    @Column
    private String descricao;

    @Column(name = "valor_objetivo")
    private BigDecimal valorObjetivo;

    @Column(name = "valor_economizado")
    private BigDecimal valorEconomizado = BigDecimal.ZERO;

    @Column
    private Integer prazo;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "caixa")
    private List<ItemCaixaEconomia> itens = new ArrayList<>();

    public CaixaEconomia(Integer id) {
        this.id = id;
    }

    public CaixaEconomiaDTO toDTO() {
        return CaixaEconomiaDTO.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .valorObjetivo(valorObjetivo)
                .valorEconomizado(NumericUtils.zeroIfNull(valorEconomizado))
                .prazo(prazo)
                .dataCriacao(dataCriacao)
                .itens(itens.stream().map(ItemCaixaEconomia::toDTO).collect(Collectors.toList()))
                .usuarioId(Objects.nonNull(usuario) ? usuario.getId() : null)
                .build();
    }
}
