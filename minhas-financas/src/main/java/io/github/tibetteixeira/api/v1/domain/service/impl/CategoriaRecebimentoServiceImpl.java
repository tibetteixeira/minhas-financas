package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.RecebimentoException;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;
import io.github.tibetteixeira.api.v1.domain.repository.CategoriaRecebimentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaRecebimentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaRecebimentoServiceImpl implements CategoriaRecebimentoService {

    private CategoriaRecebimentoRepository repository;

    @Override
    public void salvar(CategoriaRecebimento categoria) {
        repository.save(categoria);
    }

    @Override
    public void atualizar(Integer id, CategoriaRecebimento categoria) {
        CategoriaRecebimento categoriaDaBase = buscarPorId(id);
        categoriaDaBase.setDescricao(categoria.getDescricao());
        repository.save(categoriaDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public CategoriaRecebimento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecebimentoException("Categoria não encontrada."));
    }

    @Override
    public List<CategoriaRecebimento> buscarCategoriaPorDescricao(String descricao) {
        return repository.findByDescricaoContainsIgnoreCase(descricao);
    }
}
