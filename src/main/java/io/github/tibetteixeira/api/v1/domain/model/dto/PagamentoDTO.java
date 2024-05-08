package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime dataPagamento;
    private FormaPagamento formaPagamento;
    private Integer usuarioId;
    private Integer faturaId;

    public Pagamento toModel() {
        return Pagamento.builder()
                .id(id)
                .valor(valor)
                .descricao(descricao)
                .dataPagamento(dataPagamento)
                .formaPagamento(formaPagamento)
                .usuario(new Usuario(usuarioId))
                .fatura(Objects.nonNull(faturaId) ? new Fatura(faturaId) : null)
                .build();
    }
}
