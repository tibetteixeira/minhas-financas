package io.github.tibetteixeira.api.v1.domain.service;

public interface CrudService<T, Tid> {

    void salvar(T object);
    void atualizar(Tid id, T object);
    void remover(Tid id);
    T buscarPorId(Tid id);
}
