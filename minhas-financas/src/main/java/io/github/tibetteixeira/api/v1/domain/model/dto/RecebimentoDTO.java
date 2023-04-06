package io.github.tibetteixeira.api.v1.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoRecebimento;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecebimentoDTO {

    private Integer id;
    private Date dataRecebimento;
    private BigDecimal valor;
    private String descricao;
    private TipoRecebimento tipoRecebimento;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UsuarioDTO usuario;

    public Recebimento toModel() {
        return Recebimento.builder()
                .id(id)
                .dataRecebimento(dataRecebimento)
                .valor(valor)
                .descricao(descricao)
                .tipoRecebimento(tipoRecebimento)
                .usuario(usuario.toModel())
                .build();
    }
}
