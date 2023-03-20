package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;

import java.util.List;

public interface CategoriaGastoService extends CrudService<CategoriaGasto, Integer> {

    List<CategoriaGasto> buscarCategoriaPorDescricao(String descricao);
}
