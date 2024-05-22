package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorFatura;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.tibetteixeira.api.util.NumericUtils.*;
import static io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura.*;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class FaturaServiceImpl implements FaturaService {

    private final FaturaRepository repository;
    private final CartaoService cartaoService;
    private final UsuarioLogado usuarioLogado;
    private final ValidadorFatura validador;
    private final Relogio relogio;

    @Override
    public void salvar(Fatura fatura) {
        // Fatura deve ser criada apenas pelo método criarOuBuscarFaturaDoCartaoPorMesAno
    }

    @Override
    public void atualizar(Integer id, Fatura fatura) {
        atualizarStatus(fatura);
        repository.save(fatura);
    }

    @Override
    public void remover(Integer id) {
        // Uma fatura nunca deve ser removida
    }

    @Override
    public Fatura buscarPorId(Integer id) {
        validador.validar(id);
        return repository.buscarPorId(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.FATURA_NAO_ENCONTRADA));
    }

    @Override
    public List<Fatura> buscarPorCartao(Integer cartaoId) {
        validador.validar(cartaoId);
        return repository.buscarPorCartao(cartaoId, usuarioLogado.getId());
    }

    @Override
    public List<Fatura> buscarTodas() {
        return repository.buscarTodas(usuarioLogado.getId());
    }

    @Override
    public Fatura criarOuBuscarFaturaDoCartaoPorMesAno(Cartao cartao, Integer ano, Integer mes) {
        validador.validar(cartao, ano, mes);
        Cartao cartaoDaBase = cartaoService.buscarPorId(cartao.getId());
        Fatura fatura = repository.buscarPorCartaoMesAno(cartaoDaBase.getId(), mes, ano, usuarioLogado.getId())
                .orElse(null);

        if (nonNull(fatura))
            return fatura;

        fatura = Fatura.builder()
                .cartao(cartaoDaBase)
                .status(ABERTO)
                .usuario(usuarioLogado.getUsuario())
                .dataVencimento(LocalDate.of(ano, mes, cartaoDaBase.getDiaVencimento()))
                .build();

        return repository.saveAndFlush(fatura);
    }

    @Override
    public List<Fatura> buscarPorStatus(StatusPagamentoFatura... status) {
        return repository.buscarPorStatus(status);
    }

    @Override
    public void atualizarStatusAtrasado(Fatura fatura) {
        if (fatura.getDataVencimento().isBefore(relogio.hoje().toLocalDate())) {
            fatura.setStatus(ATRASADO);
            repository.save(fatura);
        }
    }

    @Override
    public void atualizarStatus(Fatura fatura) {
        FaturaDTO faturaDTO = buscarEstadoAtual(fatura.getId());

        if (maiorQueOuIgualA(faturaDTO.getValorPago(), faturaDTO.getTotalGastos()))
            fatura.setStatus(PAGO);
        else if (menorQue(faturaDTO.getValorPago(), faturaDTO.getTotalGastos()) && maiorQue(faturaDTO.getValorPago(), BigDecimal.ZERO))
            fatura.setStatus(fatura.getStatus().equals(ATRASADO) ? ATRASADO : PAGO_PARCIALMENTE);
        else
            fatura.setStatus(ABERTO);
    }

    private FaturaDTO buscarEstadoAtual(Integer id) {
        return repository.buscarEstadoAtual(id);
    }
}
