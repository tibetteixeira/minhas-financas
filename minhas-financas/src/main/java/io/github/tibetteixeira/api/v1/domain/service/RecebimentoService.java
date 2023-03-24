package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;

import java.util.List;

public interface RecebimentoService extends CrudService<Recebimento, Integer> {

    List<Recebimento> buscarRecebimentoPorCategoria(Integer id);

    List<Recebimento> buscarTodas();

}
