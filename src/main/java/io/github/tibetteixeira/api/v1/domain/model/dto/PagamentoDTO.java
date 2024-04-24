package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoDTO {

    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private Date dataPagamento;
    private TipoPagamento tipoPagamento;
    private Integer usuarioId;
    private Integer faturaId;

    public Pagamento toModel() {
        return Pagamento.builder()
                .id(id)
                .valor(valor)
                .descricao(descricao)
                .dataPagamento(dataPagamento)
                .tipoPagamento(tipoPagamento)
                .usuario(new Usuario(usuarioId))
                .fatura(Objects.nonNull(faturaId) ? new Fatura(faturaId) : null)
                .build();
    }
}
