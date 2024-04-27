package io.github.tibetteixeira.util;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
@Scope("request")
public class UsuarioLogado {

    private UsuarioLogado()  {
        throw new IllegalStateException("Utility class");
    }

    public static Usuario recuperaUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = nonNull(authentication) ? authentication.getPrincipal() : null;

        if (principal instanceof Usuario)
            return (Usuario) principal;

        return null;
    }

    public static Integer getId() {
        Usuario usuario = recuperaUsuarioAutenticado();
        return nonNull(usuario) ? usuario.getId() : null;
    }
}
