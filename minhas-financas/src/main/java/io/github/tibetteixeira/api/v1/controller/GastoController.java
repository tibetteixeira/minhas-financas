package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.exception.MinhasFinancasException;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.dto.GastoDTO;
import io.github.tibetteixeira.api.v1.domain.service.GastoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = Rotas.GASTO)
public class GastoController {

    private GastoService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody GastoDTO gasto) {
        service.salvar(gasto.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody GastoDTO gasto) {
        if (BooleanUtils.isFalse(id.equals(gasto.getId())))
            throw new MinhasFinancasException("O id da rota é diferente do id do objeto");

        service.atualizar(id, gasto.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public GastoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = "/fatura/{id}")
    public List<GastoDTO> buscarPorFatura(@PathVariable Integer id) {
        return service.buscarGastoPorFatura(id).stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/categoria/{id}")
    public List<GastoDTO> buscarPorCategoria(@PathVariable Integer id) {
        return service.buscarGastoPorCategoria(id).stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<GastoDTO> buscarTodas() {
        return service.buscarTodas().stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }
}
