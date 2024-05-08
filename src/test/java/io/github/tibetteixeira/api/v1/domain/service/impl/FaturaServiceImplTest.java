package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorFatura;
import io.github.tibetteixeira.api.v1.exception.FaturaException;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class FaturaServiceImplTest {
    @Mock
    private FaturaRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @InjectMocks
    private FaturaServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorFatura validador = new ValidadorFatura();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(FaturaException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarFaturaInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Fatura não encontrada");
    }

    @Test
    public void deveriaValidarCartaoIdNuloAoBuscarPorCartao() {
        Exception exception = assertThrows(FaturaException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarFaturaInexistenteAoBuscarPorCartao() {
        when(repository.buscarPorCartao(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<Fatura> faturas = service.buscarPorCartao(1);
        assertThat(faturas).isEmpty();
    }

    @Test
    public void deveriaValidarFaturaInexistenteAoBuscarTodas() {
        when(repository.buscarTodas(anyInt())).thenReturn(new ArrayList<>());
        List<Fatura> faturas = service.buscarTodas();
        assertThat(faturas).isEmpty();
    }

    @Test
    public void deveriaValidarCartaoNuloAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(null, 2024, 1)
        );

        assertThat(exception).hasMessage("Cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarCartaoIdNuloAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Cartao cartao = new Cartao();
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2024, 1)
        );

        assertThat(exception).hasMessage("Cartão não pode ser vazio");
    }

    @Test
    public void deveriaValidarAnoNuloAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Cartao cartao = new Cartao(1);
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, null, 1)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarAnoInvalidoAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Cartao cartao = new Cartao(1);
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2001, 1)
        );

        assertThat(exception).hasMessage("A data não pode ser anterior a 2010");
    }

    @Test
    public void deveriaValidarMesNuloAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Cartao cartao = new Cartao(1);
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2024, null)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarMesInvalidoAoCriarOuBuscarFaturaDoCartaoPorMesAno() {
        Cartao cartao = new Cartao(1);
        Exception exception = assertThrows(FaturaException.class,
                () -> service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2024, 13)
        );

        assertThat(exception).hasMessage("O mês não pode ser inválido");
    }
}
