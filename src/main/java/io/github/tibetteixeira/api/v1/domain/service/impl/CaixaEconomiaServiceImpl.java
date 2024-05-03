package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.repository.CaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import io.github.tibetteixeira.api.util.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaixaEconomiaServiceImpl implements CaixaEconomiaService {

    private final CaixaEconomiaRepository repository;
    private final Relogio relogio;
    private final UsuarioLogado usuarioLogado;
    private final ValidadorCaixaEconomia validador;

    @Override
    public void salvar(CaixaEconomia caixaEconomia) {
        validador.validar(caixaEconomia);

        CaixaEconomia caixa = CaixaEconomia.builder()
                .nome(caixaEconomia.getNome())
                .descricao(caixaEconomia.getDescricao())
                .prazo(caixaEconomia.getPrazo())
                .valorObjetivo(caixaEconomia.getValorObjetivo())
                .dataCriacao(relogio.hoje())
                .usuario(usuarioLogado.getUsuario())
                .build();

        repository.save(caixa);
    }

    @Override
    public void atualizar(Integer id, CaixaEconomia caixaEconomia) {
        validador.validar(id, caixaEconomia);

        CaixaEconomia caixaEconomiaDaBase = buscarPorId(id);

        caixaEconomiaDaBase.setNome(caixaEconomia.getNome());
        caixaEconomiaDaBase.setPrazo(caixaEconomia.getPrazo());
        caixaEconomiaDaBase.setDescricao(caixaEconomia.getDescricao());
        caixaEconomiaDaBase.setValorObjetivo(caixaEconomia.getValorObjetivo());

        repository.save(caixaEconomiaDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public CaixaEconomia buscarPorId(Integer id) {
        validador.validarId(id);
        return repository.buscarPorIdEUsuario(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.CAIXINHA_ECONOMIA_NAO_ENCONTRADA));
    }

    @Override
    public List<CaixaEconomia> buscarPorNome(String nome) {
        validador.validarNome(nome);
        return repository.buscarPorNomeEUsuario(nome, usuarioLogado.getId());
    }

    @Override
    public List<CaixaEconomia> buscarTodas() {
        return repository.buscarPorUsuario(usuarioLogado.getId());
    }
}
