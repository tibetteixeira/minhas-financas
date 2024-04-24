package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.repository.PagamentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.service.PagamentoService;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;
    private final FaturaService faturaService;

    @Override
    public void salvar(Pagamento pagamento) {
        repository.save(pagamento);
    }

    @Override
    public void atualizar(Integer id, Pagamento pagamento) {
        Pagamento pagamentoDaBase = buscarPorId(id);

        pagamentoDaBase.setTipoPagamento(pagamento.getTipoPagamento());
        pagamentoDaBase.setDataPagamento(pagamento.getDataPagamento());
        pagamentoDaBase.setValor(pagamento.getValor());
        pagamentoDaBase.setDescricao(pagamento.getDescricao());

        repository.save(pagamentoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Pagamento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PAGAMENTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Pagamento> buscarPagamentoPorFatura(Integer id) {
        return repository.findByFaturaOrderByDataPagamentoDesc(faturaService.buscarPorId(id));
    }

    @Override
    public List<Pagamento> buscarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataPagamento"));
    }

    @Override
    public List<Pagamento> buscarPagamentosPorDataSemCartao(Integer ano, Mes mes) {
        return repository.findByAnoAndMesAndFaturaIsNull(ano, mes.getNumeroMes());
    }
}
