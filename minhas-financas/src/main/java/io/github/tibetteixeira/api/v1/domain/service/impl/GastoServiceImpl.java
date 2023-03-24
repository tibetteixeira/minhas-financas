package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.GastoException;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.repository.GastoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.service.GastoService;
import io.github.tibetteixeira.util.CollectionsUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GastoServiceImpl implements GastoService {

    private GastoRepository repository;
    private CategoriaGastoService categoriaGastoService;
    private FaturaService faturaService;

    @Override
    public void salvar(Gasto gasto) {
        repository.save(gasto);
    }

    @Override
    public void atualizar(Integer id, Gasto gasto) {
        Gasto gastoDaBase = buscarPorId(id);

        gastoDaBase.setDescricao(gasto.getDescricao());
        gastoDaBase.setDataGasto(gasto.getDataGasto());
        gastoDaBase.setCategoria(gasto.getCategoria());
        gastoDaBase.setValor(gasto.getValor());

        repository.save(gastoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Gasto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new GastoException("Gasto não encontrado."));
    }

    @Override
    public List<Gasto> buscarGastoPorCategoria(Integer idCategoria) {
        CategoriaGasto categoria = categoriaGastoService.buscarPorId(idCategoria);
        List<Gasto> gastos = repository.findByCategoria(categoria);

        if (CollectionsUtils.listaValida(gastos))
            return gastos;

        throw new GastoException("Não há gastos para essa categoria.");
    }

    @Override
    public List<Gasto> buscarGastoPorFatura(Integer idFatura) {
        Fatura fatura = faturaService.buscarPorId(idFatura);
        List<Gasto> gastos = repository.findByFatura(fatura);

        if (CollectionsUtils.listaValida(gastos))
            return gastos;

        throw new GastoException("Não há gastos para essa fatura.");
    }

    @Override
    public List<Gasto> buscarTodas() {
        return repository.findAll();
    }

}
