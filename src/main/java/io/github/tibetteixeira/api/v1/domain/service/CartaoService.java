package io.github.tibetteixeira.api.v1.domain.service;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;

import java.util.List;

public interface CartaoService extends CrudService<Cartao, Integer> {

    List<Cartao> buscarTodosOsCartoesPorUsuario(Integer usuarioId);

    List<Cartao> buscarTodosOsCartoesPorNome(String nomeCartao);

}
