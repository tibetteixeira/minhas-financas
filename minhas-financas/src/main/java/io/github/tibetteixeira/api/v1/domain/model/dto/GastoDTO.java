package io.github.tibetteixeira.api.v1.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GastoDTO {

    private Integer id;
    private Date dataGasto;
    private BigDecimal valor;
    private String descricao;
    private CategoriaGastoDTO categoria;
    private FaturaDTO fatura;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UsuarioDTO usuario;

    public Gasto toModel() {
        return Gasto.builder()
                .id(id)
                .dataGasto(dataGasto)
                .valor(valor)
                .descricao(descricao)
                .categoria(categoria.toModel())
                .usuario(usuario.toModel())
                .fatura(Objects.nonNull(fatura) ? fatura.toModel() : null)
                .build();
    }
}
