package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.dto.PagamentoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.PAGAMENTO)
@AllArgsConstructor
public class PagamentoController {

    private PagamentoService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody PagamentoDTO pagamento) {
        service.salvar(pagamento.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody PagamentoDTO pagamento) {
        if (BooleanUtils.isFalse(id.equals(pagamento.getId())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, pagamento.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
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

    @GetMapping(path = Rotas.DATA)
    public List<PagamentoDTO> buscarPagamentosPorDataSemCartao(@RequestParam Integer ano, @RequestParam Mes mes) {
        return service.buscarPagamentosPorDataSemCartao(ano, mes).stream()
                .map(Pagamento::toDTO)
                .collect(Collectors.toList());
    }
}
