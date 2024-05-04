package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;

import java.util.List;

public interface GastoService extends CrudService<Gasto, Integer> {

    List<Gasto> buscarPorCategoria(Integer id);

    List<Gasto> buscarTodos();

    List<Gasto> buscarPorData(Integer ano, Mes mes);
}
