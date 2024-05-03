package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;

import java.util.List;

public interface CaixaEconomiaService extends CrudService<CaixaEconomia, Integer> {

    List<CaixaEconomia> buscarPorNome(String nome);
    List<CaixaEconomia> buscarTodas();
}
