package io.github.tibetteixeira.api.configuration.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static io.github.tibetteixeira.api.util.SecurityUtil.ehBearerRequest;
import static io.github.tibetteixeira.api.util.SecurityUtil.obterToken;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        if (isFalse(ehBearerRequest(request)))
            return;

        tokenService.inativar(obterToken(request));
    }
}
