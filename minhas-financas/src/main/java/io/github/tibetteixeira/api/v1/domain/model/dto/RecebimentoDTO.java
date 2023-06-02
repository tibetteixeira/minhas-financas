package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
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
    private Integer usuarioId;

    public Recebimento toModel() {
        return Recebimento.builder()
                .id(id)
                .dataRecebimento(dataRecebimento)
                .valor(valor)
                .descricao(descricao)
                .tipoRecebimento(tipoRecebimento)
                .usuario(new Usuario(usuarioId))
                .build();
    }
}
