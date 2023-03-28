package io.github.tibetteixeira.api.v1.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoRecebimento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RecebimentoDTO {

    private Integer id;
    private Date dataRecebimento;
    private BigDecimal valor;
    private String descricao;
    private TipoRecebimento tipoRecebimento;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UsuarioDTO usuario;

    public Recebimento toModel() {
        Recebimento recebimento = new Recebimento();

        recebimento.setId(id);
        recebimento.setDataRecebimento(dataRecebimento);
        recebimento.setValor(valor);
        recebimento.setDescricao(descricao);
        recebimento.setTipoRecebimento(tipoRecebimento);
        recebimento.setUsuario(usuario.toModel());

        return recebimento;
    }
}
