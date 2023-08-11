package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.dto.CredenciaisDTO;
import io.github.tibetteixeira.api.v1.domain.model.dto.TokenDTO;
import io.github.tibetteixeira.api.v1.domain.service.security.UsuarioAuthenticationServiceImpl;
import io.github.tibetteixeira.api.v1.domain.service.security.jwt.JwtService;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.dto.UsuarioDTO;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = Rotas.USUARIO)
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioAuthenticationServiceImpl authService;
    private final JwtService jwtService;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO usuario) {
        service.salvar(usuario.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuario) {
        if (BooleanUtils.isFalse(id.equals(usuario.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, usuario.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public UsuarioDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @PostMapping(path = Rotas.AUTH)
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .email(credenciaisDTO.getEmail())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            authService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getEmail(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
