package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.LoginException;
import io.github.tibetteixeira.api.v1.domain.exception.UsuarioException;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.service.LoginService;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private UsuarioService service;

    @Override
    public Usuario login(String email, String senha) {
        try {
            return service.buscarPorEmailESenha(email, senha);
        } catch (UsuarioException e) {
            throw new LoginException("Email ou senha incorreto");
        }
    }
}
