package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.exception.GastoException;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.repository.CategoriaGastoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaGastoServiceImpl implements CategoriaGastoService {

    private final CategoriaGastoRepository repository;

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
        try {
            repository.delete(buscarPorId(id));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("ConstraintViolationException"))
                throw new GastoException(ExceptionMessage.NAO_E_POSSIVEL_REMOVER_UMA_CATEGORIA_UTILIZADA);
            throw e;
        }

    }

    @Override
    public CategoriaGasto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionMessage.CATEGORIA_GASTO_NAO_ENCONTRADA));
    }

    @Override
    public List<CategoriaGasto> buscarCategoriaPorDescricao(String descricao) {
        return repository.findByDescricaoContainsIgnoreCaseOrderByDescricao(descricao);
    }

    @Override
    public List<CategoriaGasto> buscarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
    }
}
