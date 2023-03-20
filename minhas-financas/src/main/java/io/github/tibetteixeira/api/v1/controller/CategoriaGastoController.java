package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.dto.CategoriaGastoDTO;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.CATEGORIA_GASTO)
@AllArgsConstructor
public class CategoriaGastoController {

    private CategoriaGastoService service;

    @PostMapping(path = Rotas.EMPTY)
    public void salvar(@RequestBody CategoriaGastoDTO gastoCategoria) {
        service.salvar(gastoCategoria.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody CategoriaGastoDTO categoria) {
        service.atualizar(id, categoria.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
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
}
