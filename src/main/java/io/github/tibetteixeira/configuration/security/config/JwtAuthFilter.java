package io.github.tibetteixeira.configuration.security.config;

import io.github.tibetteixeira.configuration.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.tibetteixeira.util.SecurityUtil.ehBearerRequest;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filter) throws ServletException, IOException {
        if (ehBearerRequest(request))
            jwtService.validarToken(request);

        filter.doFilter(request, response);
    }
}
