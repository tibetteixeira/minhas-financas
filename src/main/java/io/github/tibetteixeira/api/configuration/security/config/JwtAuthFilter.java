package io.github.tibetteixeira.api.configuration.security.config;

import io.github.tibetteixeira.api.configuration.security.exception.AuthenticationException;
import io.github.tibetteixeira.api.configuration.security.exception.SecurityExceptionHandler;
import io.github.tibetteixeira.api.configuration.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.tibetteixeira.api.util.SecurityUtil.ehBearerRequest;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filter) throws ServletException, IOException {
        try {
            if (ehBearerRequest(request))
                jwtService.validarToken(request);

            filter.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityExceptionHandler.handleException(response, e);
        }

    }
}
