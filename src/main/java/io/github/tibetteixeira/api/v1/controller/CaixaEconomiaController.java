package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.dto.CaixaEconomiaDTO;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CAIXA_ECONOMIA)
@RequiredArgsConstructor
public class CaixaEconomiaController {

    private final CaixaEconomiaService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody CaixaEconomiaDTO caixaEconomia) {
        service.salvar(caixaEconomia.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CaixaEconomiaDTO caixaEconomia) {
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

}
