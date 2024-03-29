package io.github.tibetteixeira.api.v1.domain.service.security;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.SENHA_INCORRETA;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UsuarioAuthenticationServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder;

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

    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getEmail());
        boolean senhasIguais = encoder.matches(usuario.getSenha(), userDetails.getPassword());

        if (senhasIguais)
            return userDetails;

        throw new SenhaInvalidaException(SENHA_INCORRETA);

    }
}
