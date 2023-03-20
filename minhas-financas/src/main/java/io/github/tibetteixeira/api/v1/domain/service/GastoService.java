package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Gasto;

import java.util.List;

public interface GastoService extends CrudService<Gasto, Integer> {

    List<Gasto> buscarGastoPorFatura(Integer id);

    List<Gasto> buscarGastoPorCategoria(Integer id);

    List<Gasto> buscarTodas();

}
