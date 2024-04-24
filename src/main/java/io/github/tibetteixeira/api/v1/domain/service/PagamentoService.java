package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;

import java.util.List;

public interface PagamentoService extends CrudService<Pagamento, Integer> {

    List<Pagamento> buscarPagamentoPorFatura(Integer id);

    List<Pagamento> buscarTodos();

    List<Pagamento> buscarPagamentosPorDataSemCartao(Integer ano, Mes mes);
}
