package io.github.tibetteixeira.api.v1.controller;


import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.dto.CartaoDTO;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CARTAO)
@AllArgsConstructor
public class CartaoController {

    private CartaoService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody CartaoDTO cartao) {
        service.salvar(cartao.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CartaoDTO cartao) {
        service.atualizar(id, cartao.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
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
