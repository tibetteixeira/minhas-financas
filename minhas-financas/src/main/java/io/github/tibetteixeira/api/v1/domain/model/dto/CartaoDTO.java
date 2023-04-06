package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartaoDTO {

    private Integer id;
    private String nome;
    private String ultimosQuatroDigitosCartao;
    private Integer diaVencimento;
    private Integer usuarioId;

    public Cartao toModel() {
        return Cartao.builder()
                .id(id)
                .nome(nome)
                .ultimosQuatroDigitosCartao(ultimosQuatroDigitosCartao)
                .diaVencimento(diaVencimento)
                .usuario(new Usuario(usuarioId))
                .build();
    }
}
