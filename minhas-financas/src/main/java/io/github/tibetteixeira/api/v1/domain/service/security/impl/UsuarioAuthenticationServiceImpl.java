package io.github.tibetteixeira.api.v1.domain.service.security.impl;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UsuarioAuthenticationServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private static final String USER = "USER";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
                throw new UsernameNotFoundException(ExceptionMessage.USUARIO_NAO_ENCONTRADO);

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(USER)
                .build();
    }
}
