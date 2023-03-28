package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.GastoException;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.repository.CategoriaGastoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaGastoServiceImpl implements CategoriaGastoService {

    private CategoriaGastoRepository repository;

    @Override
    public void salvar(CategoriaGasto categoria) {
        repository.save(categoria);
    }

    @Override
    public void atualizar(Integer id, CategoriaGasto categoria) {
        CategoriaGasto categoriaDaBase = buscarPorId(id);
        categoriaDaBase.setDescricao(categoria.getDescricao());
        repository.save(categoriaDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public CategoriaGasto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new GastoException("Categoria não encontrada."));
    }

    @Override
    public List<CategoriaGasto> buscarCategoriaPorDescricao(String descricao) {
        return repository.findByDescricaoContainsIgnoreCase(descricao);
    }

    @Override
    public List<CategoriaGasto> buscarTodos() {
        return repository.findAll();
    }
}
