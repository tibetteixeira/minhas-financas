package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

    public Usuario toModel() {
        return Usuario.builder()
                .id(id)
                .nome(nome)
                .sobrenome(sobrenome)
                .email(email)
                .senha(senha)
                .build();

    }
}
