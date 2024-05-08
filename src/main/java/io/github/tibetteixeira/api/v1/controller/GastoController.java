package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.dto.GastoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.service.GastoService;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Rotas.GASTO)
public class GastoController {

    private final GastoService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody GastoDTO gasto) {
        service.salvar(gasto.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody GastoDTO gasto) {
        if (BooleanUtils.isFalse(id.equals(gasto.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, gasto.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public GastoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.CATEGORIA_ID)
    public List<GastoDTO> buscarPorCategoria(@PathVariable Integer id) {
        return service.buscarPorCategoria(id).stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<GastoDTO> buscarTodas() {
        return service.buscarTodos().stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.DATA)
    public List<GastoDTO> buscarPorData(@RequestParam Integer ano, @RequestParam Mes mes) {
        return service.buscarPorData(ano, mes).stream()
                .map(Gasto::toDTO)
                .collect(Collectors.toList());
    }
}
