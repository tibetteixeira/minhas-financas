package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.repository.PagamentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.service.PagamentoService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorPagamento;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static io.github.tibetteixeira.api.util.NumericUtils.menorQue;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;
    private final UsuarioLogado usuarioLogado;
    private final ValidadorPagamento validador;
    private final FaturaService faturaService;
    private final Relogio relogio;

    @Override
    public void salvar(Pagamento pagamento) {
        validador.validar(pagamento);
        pagamento.setUsuario(usuarioLogado.getUsuario());
        pagamento.atualizarDataPagamento(relogio.hoje());

        Fatura fatura = faturaService.buscarPorId(pagamento.getFatura().getId());
        fatura.adicionarPagamento(pagamento.getValor());

        repository.save(pagamento);
        faturaService.atualizar(fatura.getId(), fatura);

    }

    @Override
    public void atualizar(Integer id, Pagamento pagamento) {
        validador.validar(id, pagamento);

        Pagamento pagamentoDaBase = buscarPorId(id);
        BigDecimal valorPagoDaBase = pagamentoDaBase.getValor();
        BigDecimal valorPagoAtual = pagamento.getValor();

        pagamentoDaBase.setFormaPagamento(pagamento.getFormaPagamento());
        pagamentoDaBase.setDataPagamento(pagamento.getDataPagamento());
        pagamentoDaBase.setValor(pagamento.getValor());
        pagamentoDaBase.setDescricao(pagamento.getDescricao());
        pagamentoDaBase.atualizarDataPagamento(relogio.hoje());

        Fatura fatura = faturaService.buscarPorId(pagamento.getFatura().getId());

        if (menorQue(valorPagoDaBase, valorPagoAtual))
            fatura.adicionarPagamento(valorPagoAtual.subtract(valorPagoDaBase));
        else
            fatura.removerPagamento(valorPagoDaBase.subtract(valorPagoAtual));

        repository.save(pagamentoDaBase);
        faturaService.atualizar(fatura.getId(), fatura);
    }

    @Override
    public void remover(Integer id) {
        Pagamento pagamento = buscarPorId(id);
        repository.delete(pagamento);

        Fatura fatura = faturaService.buscarPorId(pagamento.getFatura().getId());
        fatura.removerPagamento(pagamento.getValor());

        faturaService.atualizar(fatura.getId(), fatura);
    }

    @Override
    public Pagamento buscarPorId(Integer id) {
        validador.validar(id);
        return repository.buscarPorId(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PAGAMENTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Pagamento> buscarPorFatura(Integer faturaId) {
        validador.validar(faturaId);
        return repository.buscarPorFatura(faturaId, usuarioLogado.getId());
    }

    @Override
    public List<Pagamento> buscarTodos() {
        return repository.buscarTodos(usuarioLogado.getId());
    }
}
