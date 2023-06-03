package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.dto.CaixaEconomiaDTO;
import io.github.tibetteixeira.api.v1.domain.model.dto.ItemCaixaEconomiaDTO;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CAIXA_ECONOMIA)
@AllArgsConstructor
public class CaixaEconomiaController {

    private CaixaEconomiaService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody CaixaEconomiaDTO caixaEconomia) {
        service.salvar(caixaEconomia.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CaixaEconomiaDTO caixaEconomia) {
        if (BooleanUtils.isFalse(id.equals(caixaEconomia.getId())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, caixaEconomia.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public CaixaEconomiaDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.NOME)
    public List<CaixaEconomiaDTO> buscarPorNome(@PathVariable String nome) {
        return service.buscarPorNome(nome).stream()
                .map(CaixaEconomia::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<CaixaEconomiaDTO> buscarTodas() {
        return service.buscarTodas().stream()
                .map(CaixaEconomia::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(path = Rotas.ITEM_CAIXA_ECONOMIA)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarItem(@RequestBody ItemCaixaEconomiaDTO itemCaixaEconomiaDTO) {
        service.salvar(itemCaixaEconomiaDTO.toModel());
    }

    @PutMapping(path = Rotas.ITEM_CAIXA_ECONOMIA + Rotas.ID)
    public void atualizarItem(@PathVariable Integer id, @RequestBody ItemCaixaEconomiaDTO item) {
        if (BooleanUtils.isFalse(id.equals(item.getId())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, item.toModel());
    }

    @GetMapping(path = Rotas.ITEM_CAIXA_ECONOMIA + Rotas.ID)
    public ItemCaixaEconomiaDTO buscarItemPorId(@PathVariable Integer id) {
        return service.buscarItemPorId(id).toDTO();
    }

    @DeleteMapping(path = Rotas.ITEM_CAIXA_ECONOMIA + Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerItem(@PathVariable Integer id) {
        service.removerItem(id);
    }

}
