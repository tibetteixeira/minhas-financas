package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;

import java.util.List;

public interface CategoriaRecebimentoService extends CrudService<CategoriaRecebimento, Integer> {

    List<CategoriaRecebimento> buscarCategoriaPorDescricao(String descricao);
}
