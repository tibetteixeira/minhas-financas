package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.dto.RecebimentoDTO;
import io.github.tibetteixeira.api.v1.domain.service.RecebimentoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = Rotas.RECEBIMENTO)
public class RecebimentoController {

    private RecebimentoService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody RecebimentoDTO recebimento) {
        service.salvar(recebimento.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody RecebimentoDTO recebimento) {
        service.atualizar(id, recebimento.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public RecebimentoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = "/categoria/{id}")
    public List<RecebimentoDTO> buscarPorCategoria(@PathVariable Integer id) {
        return service.buscarRecebimentoPorCategoria(id).stream()
                .map(Recebimento::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<RecebimentoDTO> buscarTodas() {
        return service.buscarTodas().stream()
                .map(Recebimento::toDTO)
                .collect(Collectors.toList());
    }
}
