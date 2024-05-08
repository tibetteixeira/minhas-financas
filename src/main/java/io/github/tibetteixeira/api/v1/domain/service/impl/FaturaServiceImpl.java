package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorFatura;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura.ABERTO;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class FaturaServiceImpl implements FaturaService {

    private final FaturaRepository repository;
    private final CartaoService cartaoService;
    private final UsuarioLogado usuarioLogado;
    private final ValidadorFatura validador;

    @Override
    public void salvar(Fatura fatura) {}

    @Override
    public void atualizar(Integer id, Fatura fatura) {
        repository.save(fatura);
    }

    @Override
    public void remover(Integer id) {}

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
        Fatura fatura = repository.buscarPorCartaoMesAno(cartaoDaBase.getId(), mes, ano)
                .orElse(null);

        if (nonNull(fatura))
            return fatura;

        fatura = Fatura.builder()
                .cartao(cartaoDaBase)
                .status(ABERTO)
                .dataVencimento(LocalDate.of(ano, mes, cartaoDaBase.getDiaVencimento()))
                .build();

        return repository.saveAndFlush(fatura);
    }
}
