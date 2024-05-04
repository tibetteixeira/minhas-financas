package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;

import java.util.List;

public interface ItemCaixaEconomiaService extends CrudService<ItemCaixaEconomia, Integer> {
    List<ItemCaixaEconomia> buscarPorCaixaEconomia(Integer caixaId);
}
