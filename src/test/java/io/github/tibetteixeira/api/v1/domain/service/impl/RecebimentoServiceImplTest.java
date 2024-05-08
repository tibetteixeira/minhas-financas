package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.repository.RecebimentoRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorRecebimento;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.RecebimentoException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.TipoRecebimento.PIX;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecebimentoServiceImplTest {

    @Mock
    private RecebimentoRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @InjectMocks
    private RecebimentoServiceImpl service;

    private Recebimento recebimento;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorRecebimento validador = new ValidadorRecebimento();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);

        recebimento = dadoRecebimento();
    }

    @Test
    public void deveriaValidarRecebimentoAoSalvar() {
        service.salvar(recebimento);
        verify(repository, times(1)).save(any(Recebimento.class));
    }

    @Test
    public void deveriaValidarRecebimentoNuloAoSalvar() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("O recebimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoSalvar() {
        recebimento.setValor(null);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoSalvar() {
        recebimento.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoSalvar() {
        recebimento.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarDescricaoNulaAoSalvar() {
        recebimento.setDescricao(null);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(recebimento)
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarTipoRecebimentoNuloAoSalvar() {
        recebimento.setTipoRecebimento(null);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.salvar(recebimento)
        );

        assertThat(exception).hasMessage("O tipo de recebimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarRecebimentoAoAtualizar() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(of(recebimento));
        service.atualizar(1, recebimento);
        verify(repository, times(1)).save(any(Recebimento.class));
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(null, recebimento)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarIdDiferenteAoAtualizar() {
        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(2, recebimento)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarRecebimentoInexistenteAoAtualizar() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("Recebimento não encontrado");
    }

    @Test
    public void deveriaValidarRecebimentoNuloAoAtualizar() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("O recebimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoAtualizar() {
        recebimento.setValor(null);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoAtualizar() {
        recebimento.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoAtualizar() {
        recebimento.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarDescricaoNulaAoAtualizar() {
        recebimento.setDescricao("");
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarTipoRecebimentoNuloAoAtualizar() {
        recebimento.setTipoRecebimento(null);
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.atualizar(1, recebimento)
        );

        assertThat(exception).hasMessage("O tipo de recebimento não pode ser vazio");
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarRecebimentoInexistenteAoRemover() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Recebimento não encontrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarRecebimentoInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Recebimento não encontrado");
    }

    @Test
    public void deveriaValidarDescricaoVaziaAoBuscarPorDescricao() {
        Exception exception = assertThrows(RecebimentoException.class,
                () -> service.buscarPorDescricao(null)
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarRecebimentoInexistenteAoBuscarPorDescricao() {
        when(repository.buscarPorDescricao(anyString(), anyInt())).thenReturn(new ArrayList<>());
        List<Recebimento> recebimentos = service.buscarPorDescricao("descrição");
        assertThat(recebimentos).isEmpty();
    }

    @Test
    public void deveriaValidarRecebimentoInexistenteAoBuscarTodos() {
        when(repository.buscarTodos(anyInt())).thenReturn(new ArrayList<>());
        List<Recebimento> recebimentos = service.buscarTodos();
        assertThat(recebimentos).isEmpty();
    }

    private Recebimento dadoRecebimento() {
        return Recebimento.builder()
                .id(1)
                .descricao("Recebimento da descricao")
                .valor(BigDecimal.TEN)
                .tipoRecebimento(PIX)
                .build();
    }
}
