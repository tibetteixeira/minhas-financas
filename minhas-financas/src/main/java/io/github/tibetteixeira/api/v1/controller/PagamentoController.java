package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.dto.PagamentoDTO;
import io.github.tibetteixeira.api.v1.domain.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.PAGAMENTO)
@AllArgsConstructor
public class PagamentoController {

    private PagamentoService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody PagamentoDTO gasto) {
        service.salvar(gasto.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody PagamentoDTO gasto) {
        service.atualizar(id, gasto.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public PagamentoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = "/fatura/{id}")
    public List<PagamentoDTO> buscarPorFatura(@PathVariable Integer id) {
        return service.buscarPagamentoPorFatura(id).stream()
                .map(Pagamento::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<PagamentoDTO> buscarTodas() {
        return service.buscarTodos().stream()
                .map(Pagamento::toDTO)
                .collect(Collectors.toList());
    }
}
