package io.github.tibetteixeira.api.v1.domain.service.security.jwt;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.config.SecurityConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private static final String BEARER = "Bearer";

    private static final String SPLITTER = " ";

    public static final String SPRING_SECURITY_SESSION = "SPRING_SECURITY_CONTEXT";

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filter) throws ServletException, IOException {

        String authorization = request.getHeader(SecurityConfig.getAuthorization());

        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(BEARER))
            validarAuthorization(authorization, request);

        filter.doFilter(request, response);
    }

    private void validarAuthorization(String authorization, HttpServletRequest request) {
        String token = authorization.split(SPLITTER)[1];
        UsernamePasswordAuthenticationToken user = null;

        if (jwtService.tokenValido(token))
            user = autenticarUsuario(token, request);

        SecurityContextHolder.getContext().setAuthentication(user);
        request.getSession().setAttribute(SPRING_SECURITY_SESSION, SecurityContextHolder.getContext());
    }

    private UsernamePasswordAuthenticationToken autenticarUsuario(String token, HttpServletRequest request) {
        String emailUsuario = jwtService.obterLoginUsuario(token);
        Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(emailUsuario);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return user;
    }
}
