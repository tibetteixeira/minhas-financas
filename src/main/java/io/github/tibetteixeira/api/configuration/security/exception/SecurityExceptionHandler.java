package io.github.tibetteixeira.api.configuration.security.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

@Component
public class SecurityExceptionHandler {

    private SecurityExceptionHandler(){}

    public static void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("text/plain; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(format("Error: %s", e.getMessage()));
    }
}
