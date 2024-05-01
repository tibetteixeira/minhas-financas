package io.github.tibetteixeira.api.util;

import io.github.tibetteixeira.api.configuration.security.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class SecurityUtil {

    public static final String SPRING_SECURITY_SESSION = "SPRING_SECURITY_CONTEXT";
    public static final String BEARER = "Bearer";
    public static final String SPLITTER = " ";

    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean ehBearerRequest(HttpServletRequest request) {
        String authorization = SecurityConfig.getAuthorization(request);
        return StringUtils.isNotBlank(authorization) && authorization.startsWith(BEARER);
    }

    public static String obterToken(HttpServletRequest request) {
        String authorization = SecurityConfig.getAuthorization(request);
        return authorization.split(SPLITTER)[1];
    }

    public static Claims obterClaims(String token, String chaveAssinatura) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(getChaveAssinatura(chaveAssinatura))
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    public static boolean tokenValido(String token, String chaveAssinatura) {
        try {
            Claims claims = obterClaims(token, chaveAssinatura);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime dateTimeExpiracao = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return dateTimeExpiracao.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    public static String obterLoginUsuario(String token, String chaveAssinatura) throws ExpiredJwtException {
        return obterClaims(token, chaveAssinatura).getSubject();
    }

    public static Key getChaveAssinatura(String chaveAssinatura) {
        byte[] keyBytes = Decoders.BASE64.decode(chaveAssinatura);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
