package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;

import java.util.List;

public interface FaturaService extends CrudService<Fatura, Integer> {
    Fatura buscarPorId(Integer id);

    Fatura criarOuBuscarFaturaDoCartaoPorMesAno(Cartao cartao, Integer ano, Integer mes);

    List<Fatura> buscarPorCartao(Integer cartaoId);

    List<Fatura> buscarTodas();

    List<Fatura> buscarPorStatus(StatusPagamentoFatura... status);

    void atualizarStatus(Fatura fatura);

    void atualizarStatusAtrasado(Fatura fatura);
}
