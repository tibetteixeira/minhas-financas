package io.github.tibetteixeira.api.v1.controller;


import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.dto.CartaoDTO;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CARTAO)
@AllArgsConstructor
public class CartaoController {

    private CartaoService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody CartaoDTO cartao) {
        service.salvar(cartao.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CartaoDTO cartao) {
        if (BooleanUtils.isFalse(id.equals(cartao.getId())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, cartao.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public CartaoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.NOME)
    public List<CartaoDTO> buscarPorNome(@PathVariable String nome) {
        return service.buscarTodosOsCartoesPorNome(nome).stream()
                .map(Cartao::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.USUARIO_ID)
    public List<CartaoDTO> buscarPorUsuario(@PathVariable Integer usuario) {
        return service.buscarTodosOsCartoesPorUsuario(usuario).stream()
                .map(Cartao::toDTO)
                .collect(Collectors.toList());
    }
}
