package io.github.tibetteixeira.api.configuration.security.service;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.configuration.security.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static io.github.tibetteixeira.api.util.SecurityUtil.*;
import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public static final String TOKEN_INVALIDO = "Token inválido";

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(HttpServletRequest request, Usuario usuario) {
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(parseLong(this.expiracao));
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        String chave = Jwts.builder()
                .subject(usuario.getEmail())
                .expiration(data)
                .signWith(getChaveAssinatura(this.chaveAssinatura), SignatureAlgorithm.HS384)
                .compact();

        tokenService.salvar(chave, usuario);
        autenticarToken(request, chave);

        return chave;
    }

    public void validarToken(HttpServletRequest request) {
        String token = obterToken(request);

        if (isFalse(tokenService.tokenValido(token)))
            throw new AuthenticationException(TOKEN_INVALIDO);

        if (isFalse(tokenValido(token, this.chaveAssinatura))) {
            tokenService.inativar(token);
            throw new AuthenticationException(TOKEN_INVALIDO);
        }

        autenticarToken(request, token);
    }

    private void autenticarToken(HttpServletRequest request, String token) {
        UsernamePasswordAuthenticationToken authToken = autenticarUsuario(token, request);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        request.getSession().setAttribute(SPRING_SECURITY_SESSION, SecurityContextHolder.getContext());
    }

    private UsernamePasswordAuthenticationToken autenticarUsuario(String token, HttpServletRequest request) {
        String emailUsuario = obterLoginUsuario(token, chaveAssinatura);
        Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(emailUsuario);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authToken;
    }
}
