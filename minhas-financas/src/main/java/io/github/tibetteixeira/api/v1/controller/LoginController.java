package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.dto.LoginDTO;
import io.github.tibetteixeira.api.v1.domain.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Rotas.LOGIN)
public class LoginController {

    private LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @GetMapping(path = Rotas.EMPTY)
    public Usuario login(@RequestBody LoginDTO login) {
        return service.login(login.getEmail(), login.getSenha());
    }
}
