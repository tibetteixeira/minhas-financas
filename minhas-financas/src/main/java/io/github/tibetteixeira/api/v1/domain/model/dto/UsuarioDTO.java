package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

    public Usuario toModel() {

        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        return usuario;
    }
}
