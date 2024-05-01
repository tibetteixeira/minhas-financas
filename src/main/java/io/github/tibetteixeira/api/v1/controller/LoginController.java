package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.dto.CredenciaisDTO;
import io.github.tibetteixeira.api.v1.domain.model.dto.TokenDTO;
import io.github.tibetteixeira.api.configuration.security.service.UsuarioAuthenticationService;
import io.github.tibetteixeira.api.configuration.security.service.JwtService;
import io.github.tibetteixeira.api.v1.exception.SenhaInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = Rotas.AUTH)
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioAuthenticationService authService;
    private final JwtService jwtService;

    @PostMapping(path = Rotas.EMPTY)
    public TokenDTO autenticar(HttpServletRequest request, @RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .email(credenciaisDTO.getEmail())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            Usuario usuarioAutenticado = (Usuario) authService.autenticar(usuario);
            String token = jwtService.gerarToken(request, usuarioAutenticado);
            return new TokenDTO(usuarioAutenticado.getEmail(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
