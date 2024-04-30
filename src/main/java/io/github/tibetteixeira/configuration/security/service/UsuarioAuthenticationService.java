package io.github.tibetteixeira.configuration.security.service;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioAuthenticationService {

    private final UsuarioService usuarioService;

    private static final String USER = "USER";

    public UserDetails autenticar(Usuario usuario) {
        Usuario usuarioLogado = usuarioService.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());

        UserDetails user = User.builder()
                .username(usuarioLogado.getUsername())
                .password(usuarioLogado.getPassword())
                .roles(USER)
                .build();

        usuarioLogado.setAuthorities(user.getAuthorities());

        return usuarioLogado;
    }
}
