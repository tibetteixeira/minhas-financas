package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.*;
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
public class GastoDTO {

    private Integer id;
    private LocalDateTime dataGasto;
    private BigDecimal valor;
    private String descricao;
    private FormaPagamento formaPagamento;
    private Integer categoriaId;
    private Integer faturaId;
    private Integer usuarioId;
    private Integer cartao;

    public Gasto toModel() {
        return Gasto.builder()
                .id(id)
                .dataGasto(dataGasto)
                .valor(valor)
                .descricao(descricao)
                .formaPagamento(formaPagamento)
                .categoria(new CategoriaGasto(categoriaId))
                .cartao(new Cartao(cartao))
                .usuario(new Usuario(usuarioId))
                .fatura(Objects.nonNull(faturaId) ? new Fatura(faturaId) : null)
                .build();
    }
}
