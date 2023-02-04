package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {

    Usuario buscarPorEmailESenha(String email, String senha);

    Usuario buscarPorEmail(String email);
}
