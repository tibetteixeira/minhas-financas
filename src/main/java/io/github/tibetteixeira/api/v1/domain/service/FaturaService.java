package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;

import java.util.List;

public interface FaturaService extends CrudService<Fatura, Integer> {

    Fatura buscarFaturaDoCartaoPorMesAno(Fatura fatura);

    List<Fatura> buscarFaturaPorCartao(Integer cartaoId);

    Fatura buscaOuSalva(Fatura fatura);
}
