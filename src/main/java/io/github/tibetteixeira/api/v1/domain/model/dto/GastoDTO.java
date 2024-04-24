package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
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
    private Integer categoriaId;
    private Integer faturaId;
    private Integer usuarioId;

    public Gasto toModel() {
        return Gasto.builder()
                .id(id)
                .dataGasto(dataGasto)
                .valor(valor)
                .descricao(descricao)
                .categoria(new CategoriaGasto(categoriaId))
                .usuario(new Usuario(usuarioId))
                .fatura(Objects.nonNull(faturaId) ? new Fatura(faturaId) : null)
                .build();
    }
}
