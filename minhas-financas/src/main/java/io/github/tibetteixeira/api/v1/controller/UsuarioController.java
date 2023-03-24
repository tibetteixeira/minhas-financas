package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.exception.MinhasFinancasException;
import io.github.tibetteixeira.api.v1.domain.model.dto.UsuarioDTO;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Rotas.USUARIO)
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody UsuarioDTO usuario) {
        service.salvar(usuario.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuario) {
        if (BooleanUtils.isFalse(id.equals(usuario.getId())))
            throw new MinhasFinancasException("O id da rota é diferente do id do objeto");

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
