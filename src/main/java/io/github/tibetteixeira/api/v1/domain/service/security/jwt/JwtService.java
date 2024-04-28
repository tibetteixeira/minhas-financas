package io.github.tibetteixeira.api.v1.domain.service.security.jwt;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.Long.parseLong;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(parseLong(this.expiracao));
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(usuario.getEmail())
                .expiration(data)
                .signWith(getChaveAssinatura(), SignatureAlgorithm.HS384)
                .compact();
    }

    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(getChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime dateTimeExpiracao = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return dateTimeExpiracao.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }

    private Key getChaveAssinatura() {
        byte[] keyBytes = Decoders.BASE64.decode(this.chaveAssinatura);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
