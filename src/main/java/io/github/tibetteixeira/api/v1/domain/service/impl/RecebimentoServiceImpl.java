package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.repository.RecebimentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.RecebimentoService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorRecebimento;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class RecebimentoServiceImpl implements RecebimentoService {

    private final RecebimentoRepository repository;
    private final UsuarioLogado usuarioLogado;
    private final ValidadorRecebimento validador;
    private final Relogio relogio;

    @Override
    public void salvar(Recebimento recebimento) {
        validador.validar(recebimento);

        recebimento.setUsuario(usuarioLogado.getUsuario());
        recebimento.atualizarDataRecebimento(relogio.hoje());

        repository.save(recebimento);
    }

    @Override
    public void atualizar(Integer id, Recebimento recebimento) {
        validador.validar(id, recebimento);

        Recebimento recebimentoDaBase = buscarPorId(id);
        recebimentoDaBase.setDescricao(recebimento.getDescricao());
        recebimentoDaBase.setDataRecebimento(recebimento.getDataRecebimento());
        recebimentoDaBase.setTipoRecebimento(recebimento.getTipoRecebimento());
        recebimentoDaBase.setValor(recebimento.getValor());
        recebimento.atualizarDataRecebimento(relogio.hoje());

        repository.save(recebimentoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Recebimento buscarPorId(Integer id) {
        validador.validar(id);
        return repository.buscarPorId(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.RECEBIMENTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Recebimento> buscarPorDescricao(String descricao) {
        validador.validarDescricao(descricao);
        return repository.buscarPorDescricao(descricao, usuarioLogado.getId());
    }

    @Override
    public List<Recebimento> buscarTodos() {
        return repository.buscarTodos(usuarioLogado.getId());
    }

}
