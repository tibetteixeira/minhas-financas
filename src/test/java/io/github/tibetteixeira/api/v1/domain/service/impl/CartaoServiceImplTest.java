package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.CartaoRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCartao;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.CartaoException;
import io.github.tibetteixeira.api.util.UsuarioLogado;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CartaoServiceImplTest {

    @Mock
    private UsuarioLogado usuarioLogado;
    @Mock
    private CartaoRepository repository;
    @InjectMocks
    private CartaoServiceImpl service;

    private Cartao cartao;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorCartao validador = new ValidadorCartao();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);

        cartao = dadoCartao();
    }

    @Test
    public void deveriaValidarCartaoNuloAoSalvar() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarNomeCartaoVazioAoSalvar() {
        cartao.setNome("");

        Exception exception = assertThrows(CartaoException.class,
                () -> service.salvar(cartao)
        );

        assertThat(exception).hasMessage("Nome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUltimosQuatroDigitosCartaoVazioAoSalvar() {
        cartao.setUltimosQuatroDigitosCartao("");

        Exception exception = assertThrows(CartaoException.class,
                () -> service.salvar(cartao)
        );

        assertThat(exception).hasMessage("Os últimos quatro dígitos não pode ser vazio");
    }

    @Test
    public void deveriaValidarDiaVencimentoNuloAoSalvar() {
        cartao.setDiaVencimento(null);

        Exception exception = assertThrows(CartaoException.class,
                () -> service.salvar(cartao)
        );

        assertThat(exception).hasMessage("O dia do vencimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarDiaVencimentoInvalidoAoSalvar() {
        cartao.setDiaVencimento(32);

        Exception exception = assertThrows(CartaoException.class,
                () -> service.salvar(cartao)
        );

        assertThat(exception).hasMessage("O dia do vencimento não pode ser um valor inválido");
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(null, cartao)
        );

        assertThat(exception).hasMessage("Id do cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarCartaoNuloAoAtualizar() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("Cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarNomeCartaoVazioAoAtualizar() {
        cartao.setNome("");

        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("Nome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUltimosQuatroDigitosCartaoVazioAoAtualizar() {
        cartao.setUltimosQuatroDigitosCartao("");

        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("Os últimos quatro dígitos não pode ser vazio");
    }

    @Test
    public void deveriaValidarDiaVencimentoNuloAoAtualizar() {
        cartao.setDiaVencimento(null);

        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("O dia do vencimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarDiaVencimentoInvalidoAoAtualizar() {
        cartao.setDiaVencimento(32);

        Exception exception = assertThrows(CartaoException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("O dia do vencimento não pode ser um valor inválido");
    }

    @Test
    public void deveriaValidarIdDaRotaDiferenteDoCartaoAoAtualizar() {
        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(2, cartao)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarAtualizacaoDoCartaoDeOutroUsuario() {
        when(usuarioLogado.getId()).thenReturn(2);
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("Cartão não encontrado");
    }

    @Test
    public void deveriaValidarAtualizacaoDeCartaoInexistente() {
        when(repository.findByIdAndUsuario(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, cartao)
        );

        assertThat(exception).hasMessage("Cartão não encontrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("Id do cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarCartaoInexistenteAoBuscarPorId() {
        when(repository.findByIdAndUsuario(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Cartão não encontrado");
    }

    @Test
    public void deveriaValidarCartaoInexistenteAoBuscarTodosOsCartoesPorUsuario() {
        when(repository.findByUsuario(anyInt())).thenReturn(new ArrayList<>());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarTodosOsCartoesPorUsuario()
        );

        assertThat(exception).hasMessage("Este usuário não possui cartão cadastrado");
    }

    @Test
    public void deveriaValidarNomeVazioAoBuscarTodosOsCartoesPorNome() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.buscarTodosOsCartoesPorNome(null)
        );

        assertThat(exception).hasMessage("Nome não pode ser vazio");
    }

    @Test
    public void deveriaValidarCartaoInexistenteAoBuscarTodosOsCartoesPorNome() {
        when(repository.findByUsuarioAndNome(anyInt(), anyString())).thenReturn(new ArrayList<>());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarTodosOsCartoesPorNome("Bank")
        );

        assertThat(exception).hasMessage("Não existe cartão com esse nome");
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(CartaoException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("Id do cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarCartaoInexistenteAoRemover() {
        when(repository.findByIdAndUsuario(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Cartão não encontrado");
    }

    private Cartao dadoCartao() {
        return Cartao.builder()
                .id(1)
                .nome("MeuBank")
                .diaVencimento(8)
                .ultimosQuatroDigitosCartao("1010")
                .usuario(dadoUsuario())
                .build();
    }

    private Usuario dadoUsuario() {
        return Usuario.builder()
                .id(1)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .email("email@email.com")
                .senha("123")
                .build();
    }
}
