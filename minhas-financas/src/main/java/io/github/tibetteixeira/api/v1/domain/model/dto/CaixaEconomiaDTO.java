package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaixaEconomiaDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal valorObjetivo;
    private BigDecimal valorEconomizado;
    private Integer prazo;
    private Date dataCriacao;
    private Usuario usuario;
    private List<ItemCaixaEconomiaDTO> itens = new ArrayList<>();

    public CaixaEconomia toModel() {
        CaixaEconomia caixaEconomia = new CaixaEconomia();

        caixaEconomia.setId(id);
        caixaEconomia.setNome(nome);
        caixaEconomia.setDescricao(descricao);
        caixaEconomia.setValorObjetivo(valorObjetivo);
        caixaEconomia.setValorEconomizado(valorEconomizado);
        caixaEconomia.setPrazo(prazo);
        caixaEconomia.setDataCriacao(dataCriacao);
        caixaEconomia.setItens(itens.stream().map(ItemCaixaEconomiaDTO::toModel).collect(Collectors.toList()));

        return caixaEconomia;
    }
}
