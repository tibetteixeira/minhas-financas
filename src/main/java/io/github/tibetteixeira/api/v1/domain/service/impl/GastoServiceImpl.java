package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.repository.GastoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaGastoService;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.service.GastoService;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.util.CollectionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    private final GastoRepository repository;
    private final CategoriaGastoService categoriaGastoService;
    private final FaturaService faturaService;

    @Override
    public void salvar(Gasto gasto) {
        if (Objects.nonNull(gasto.getFatura())) {
            Fatura fatura = faturaService.buscaOuSalva(gasto.getFatura());
            gasto.setFatura(fatura);
        }

        repository.save(gasto);
    }

    @Override
    public void atualizar(Integer id, Gasto gasto) {
        Gasto gastoDaBase = buscarPorId(id);

        if (Objects.nonNull(gasto.getFatura())) {
            Fatura fatura = faturaService.buscaOuSalva(gasto.getFatura());
            gasto.setFatura(fatura);
        }

        gastoDaBase.setDescricao(gasto.getDescricao());
        gastoDaBase.setDataGasto(gasto.getDataGasto());
        gastoDaBase.setCategoria(gasto.getCategoria());
        gastoDaBase.setValor(gasto.getValor());
        gastoDaBase.setFatura(gasto.getFatura());

        repository.save(gastoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Gasto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.GASTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Gasto> buscarGastoPorCategoria(Integer idCategoria) {
        CategoriaGasto categoria = categoriaGastoService.buscarPorId(idCategoria);
        List<Gasto> gastos = repository.findByCategoriaOrderByDataGastoDesc(categoria);

        if (CollectionsUtils.listaValida(gastos))
            return gastos;

        throw new NotFoundException(ExceptionMessage.NAO_HA_GASTOS_PARA_ESSA_CATEGORIA);
    }

    @Override
    public List<Gasto> buscarGastoPorFatura(Integer idFatura) {
        Fatura fatura = faturaService.buscarPorId(idFatura);
        List<Gasto> gastos = repository.findByFaturaOrderByDataGastoDesc(fatura);

        if (CollectionsUtils.listaValida(gastos))
            return gastos;

        throw new NotFoundException(ExceptionMessage.NAO_HA_GASTOS_PARA_ESSA_FATURA);
    }

    @Override
    public List<Gasto> buscarTodas() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataGasto"));
    }

    @Override
    public List<Gasto> buscarGastosPorDataSemCartao(Integer ano, Mes mes) {
        return repository.findByAnoAndMesAndFaturaIsNull(ano, mes.getNumeroMes());
    }
}
