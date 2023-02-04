package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;

public interface LoginService {

    Usuario login(String email, String senha);
}
