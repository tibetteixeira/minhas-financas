package io.github.tibetteixeira.api.v1.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.CaixaEconomiaDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "caixa_economia")
public class CaixaEconomia {

    @Id
    @GeneratedValue(generator = "caixa_economia_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "caixa_economia_id_seq", sequenceName = "caixa_economia_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 100)
    @NonNull
    private String nome;

    @Column
    private String descricao;

    @Column(name = "valor_objetivo")
    private BigDecimal valorObjetivo;

    @Column(name = "valor_economizado")
    private BigDecimal valorEconomizado;

    @Column
    private Integer prazo;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NonNull
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "caixa")
    private List<ItemCaixaEconomia> itens = new ArrayList<>();

    public CaixaEconomiaDTO toDTO() {
        CaixaEconomiaDTO caixaEconomia = new CaixaEconomiaDTO();

        caixaEconomia.setId(id);
        caixaEconomia.setNome(nome);
        caixaEconomia.setDescricao(descricao);
        caixaEconomia.setValorObjetivo(valorObjetivo);
        caixaEconomia.setValorEconomizado(valorEconomizado);
        caixaEconomia.setPrazo(prazo);
        caixaEconomia.setDataCriacao(dataCriacao);
        caixaEconomia.setItens(itens.stream().map(ItemCaixaEconomia::toDTO).collect(Collectors.toList()));

        return caixaEconomia;
    }
}
