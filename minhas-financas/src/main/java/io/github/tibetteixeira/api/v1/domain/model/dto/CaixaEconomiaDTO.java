package io.github.tibetteixeira.api.v1.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.util.NumericUtils;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaixaEconomiaDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal valorObjetivo;
    private BigDecimal valorEconomizado;
    private Integer prazo;
    private Date dataCriacao;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer usuarioId;
    private List<ItemCaixaEconomiaDTO> itens = new ArrayList<>();

    public CaixaEconomia toModel() {
        return CaixaEconomia.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .valorObjetivo(valorObjetivo)
                .valorEconomizado(NumericUtils.zeroIfNull(valorEconomizado))
                .prazo(prazo)
                .dataCriacao(dataCriacao)
                .itens(itens.stream().map(ItemCaixaEconomiaDTO::toModel).collect(Collectors.toList()))
                .usuario(new Usuario(usuarioId))
                .build();
    }
}
