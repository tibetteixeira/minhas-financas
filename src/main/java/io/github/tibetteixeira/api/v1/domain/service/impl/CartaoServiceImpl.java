package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCartao;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.repository.CartaoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.util.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.tibetteixeira.util.CollectionsUtils.listaNaoValida;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository repository;
    private final ValidadorCartao validador;
    private final UsuarioLogado usuarioLogado;

    @Override
    public void salvar(Cartao cartao) {
        validador.validar(cartao);
        repository.save(cartao);
    }

    @Override
    public void atualizar(Integer id, Cartao cartao) {
        validador.validar(cartao, id);
        Cartao cartaoDaBase = buscarPorId(id);
        cartaoDaBase.setNome(cartao.getNome());
        cartaoDaBase.setDiaVencimento(cartao.getDiaVencimento());
        cartaoDaBase.setUltimosQuatroDigitosCartao(cartao.getUltimosQuatroDigitosCartao());

        repository.save(cartaoDaBase);
    }

    @Override
    public void remover(Integer id) {
        Cartao cartao = buscarPorId(id);
        repository.delete(cartao);
    }

    @Override
    public Cartao buscarPorId(Integer id) {
        validador.validar(id);
        return repository.findByIdAndUsuario(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.CARTAO_NAO_ENCONTRADO));
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorUsuario() {
        List<Cartao> cartoes = repository.findByUsuario(usuarioLogado.getId());

        if (listaNaoValida(cartoes))
            throw new NotFoundException(ExceptionMessage.USUARIO_NAO_POSSUI_CARTAO_CADASTRADO);

        return cartoes;
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorNome(String nomeCartao) {
        validador.validarNome(nomeCartao);
        List<Cartao> cartoes = repository.findByUsuarioAndNome(usuarioLogado.getId(), nomeCartao);

        if (listaNaoValida(cartoes))
            throw new NotFoundException(ExceptionMessage.NAO_EXISTE_CARTAO_COM_ESSE_NOME);

        return cartoes;
    }
}
