package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.dto.UsuarioDTO;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Rotas.USUARIO)
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody UsuarioDTO usuario) {
        service.salvar(usuario.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuario) {
        service.atualizar(id, usuario.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public UsuarioDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

}
