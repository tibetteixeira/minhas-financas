package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;

import java.util.List;

public interface PagamentoService extends CrudService<Pagamento, Integer> {

    Pagamento buscarPorId(Integer id);

    List<Pagamento> buscarTodos();

    List<Pagamento> buscarPorFatura(Integer faturaId);
}
