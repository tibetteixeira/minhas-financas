package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.dto.ItemCaixaEconomiaDTO;
import io.github.tibetteixeira.api.v1.domain.service.ItemCaixaEconomiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.ITEM_CAIXA_ECONOMIA)
@RequiredArgsConstructor
public class ItemCaixaEconomiaController {

    private final ItemCaixaEconomiaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody ItemCaixaEconomiaDTO itemCaixaEconomiaDTO) {
        service.salvar(itemCaixaEconomiaDTO.toModel());
    }

    @GetMapping(path = Rotas.ID)
    public ItemCaixaEconomiaDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.CAIXA_ID)
    public List<ItemCaixaEconomiaDTO> buscarPorCaixa(@PathVariable Integer caixa) {
        return service.buscarPorCaixaEconomia(caixa).stream()
                .map(ItemCaixaEconomia::toDTO)
                .collect(Collectors.toList());
    }
}
