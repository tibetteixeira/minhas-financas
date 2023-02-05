package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;

import java.util.List;

public interface FaturaService extends CrudService<Fatura, Integer> {

    List<Fatura> buscarFaturaPorCartao(Integer cartaoId);
}
