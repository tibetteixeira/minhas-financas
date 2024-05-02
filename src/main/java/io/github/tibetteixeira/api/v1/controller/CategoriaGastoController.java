package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.dto.CategoriaGastoDTO;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import io.github.tibetteixeira.api.v1.exception.MinhasFinancasException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CATEGORIA_GASTO)
@RequiredArgsConstructor
public class CategoriaGastoController {

    private final CategoriaGastoService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody CategoriaGastoDTO gastoCategoria) {
        throw new MinhasFinancasException("Funcionalidade em desenvolvimento");
//        service.salvar(gastoCategoria.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CategoriaGastoDTO categoria) {
        throw new MinhasFinancasException("Funcionalidade em desenvolvimento");
//        service.atualizar(id, categoria.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        throw new MinhasFinancasException("Funcionalidade em desenvolvimento");
//        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public CategoriaGastoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.DESCRICAO)
    public List<CategoriaGastoDTO> buscarPorDescricao(@PathVariable String descricao) {
        return service.buscarCategoriaPorDescricao(descricao).stream()
                .map(CategoriaGasto::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<CategoriaGastoDTO> buscarTodos() {
        return service.buscarTodos().stream()
                .map(CategoriaGasto::toDTO)
                .collect(Collectors.toList());
    }
}
