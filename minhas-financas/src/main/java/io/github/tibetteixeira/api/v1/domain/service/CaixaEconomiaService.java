package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;

import java.util.List;

public interface CaixaEconomiaService extends CrudService<CaixaEconomia, Integer> {

    List<CaixaEconomia> buscarPorNome(String nome);
    List<CaixaEconomia> buscarTodas();

    void salvar(ItemCaixaEconomia itemCaixaEconomia);
    void atualizar(Integer id, ItemCaixaEconomia itemCaixaEconomia);
    ItemCaixaEconomia buscarItemPorId(Integer id);
    void removerItem(Integer id);
}
