package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartaoDTO {

    private Integer id;
    private String nome;
    private String ultimosQuatroDigitosCartao;
    private Integer diaVencimento;
    private Integer usuarioId;

    public Cartao toModel() {
        Cartao cartao = new Cartao();

        cartao.setId(id);
        cartao.setNome(nome);
        cartao.setUltimosQuatroDigitosCartao(ultimosQuatroDigitosCartao);
        cartao.setDiaVencimento(diaVencimento);
        cartao.setUsuario(new Usuario(usuarioId));

        return cartao;
    }
}
