package io.github.tibetteixeira.util;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioLogado {

    public static Usuario recuperaUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = nonNull(authentication) ? authentication.getPrincipal() : null;

        if (principal instanceof Usuario)
            return (Usuario) principal;

        return null;
    }

    public Integer getId() {
        Usuario usuario = recuperaUsuarioAutenticado();
        return nonNull(usuario) ? usuario.getId() : null;
    }

    public Usuario getUsuario() {
        return recuperaUsuarioAutenticado();
    }
}
