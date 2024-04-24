package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.CartaoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.tibetteixeira.util.CollectionsUtils.listaNaoValida;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository repository;

    private final UsuarioService usuarioService;

    @Override
    public void salvar(Cartao cartao) {
        repository.save(cartao);
    }

    @Override
    public void atualizar(Integer id, Cartao cartao) {
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
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.CARTAO_NAO_ENCONTRADO));
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorUsuario(Integer usuarioId) {
        Usuario usuarioDaBase = usuarioService.buscarPorId(usuarioId);

        List<Cartao> cartoes = repository.findCartaoByUsuarioOrderById(usuarioDaBase);

        if (listaNaoValida(cartoes))
            throw new NotFoundException(ExceptionMessage.USUARIO_NAO_POSSUI_CARTAO_CADASTRADO);

        return cartoes;
    }

    @Override
    public List<Cartao> buscarTodosOsCartoesPorNome(String nomeCartao) {
        List<Cartao> cartoes = repository.findByNomeContainsIgnoreCaseOrderById(nomeCartao);

        if (listaNaoValida(cartoes))
            throw new NotFoundException(ExceptionMessage.NAO_EXISTE_CARTAO_COM_ESSE_NOME);

        return cartoes;
    }
}
