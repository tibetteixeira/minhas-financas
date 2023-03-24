package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.CartaoException;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.CartaoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.tibetteixeira.util.CollectionsUtils.listaNaoValida;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@AllArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private CartaoRepository repository;

    private UsuarioService usuarioService;

    @Override
    public void salvar(Cartao cartao) {
        repository.save(cartao);
    }

    @Override
    public void atualizar(Integer id, Cartao cartao) {
        if (isFalse(id.equals(cartao.getId())))
            throw new CartaoException("Id do cartão diferente do id da Url.");

        buscarPorId(id);

        repository.save(cartao);
    }

    @Override
    public void remover(Integer id) {
        Cartao cartao = buscarPorId(id);
        repository.delete(cartao);
    }

    @Override
    public Cartao buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CartaoException("Cartão não encontrado."));
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorUsuario(Integer usuarioId) {
        Usuario usuarioDaBase = usuarioService.buscarPorId(usuarioId);

        List<Cartao> cartoes = repository.findCartaoByUsuario(usuarioDaBase);

        if (listaNaoValida(cartoes))
            throw new CartaoException("Este usuário não possui cartão cadastrado.");

        return cartoes;
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorNome(String nomeCartao) {
        List<Cartao> cartoes = repository.findByNomeContainsIgnoreCase(nomeCartao);

        if (listaNaoValida(cartoes))
            throw new CartaoException("Não existe cartão com esse nome.");

        return cartoes;
    }
}
