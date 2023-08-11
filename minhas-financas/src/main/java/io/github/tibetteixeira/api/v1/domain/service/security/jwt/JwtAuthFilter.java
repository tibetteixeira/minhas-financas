package io.github.tibetteixeira.api.v1.domain.service.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer";

    private static final String SPLITTER = " ";

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(BEARER)) {
            String token = authorization.split(SPLITTER)[1];

            if (jwtService.tokenValido(token)) {
                String emailUsuario = jwtService.obterLoginUsuario(token);
                UserDetails usuario = userDetailsService.loadUserByUsername(emailUsuario);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario,
                        null,
                        usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
