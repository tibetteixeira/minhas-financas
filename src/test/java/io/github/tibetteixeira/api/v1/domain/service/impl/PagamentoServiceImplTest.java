package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import io.github.tibetteixeira.api.v1.domain.repository.PagamentoRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorPagamento;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.PagamentoException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento.CREDITO;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class PagamentoServiceImplTest {

    @Mock
    private PagamentoRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @InjectMocks
    private PagamentoServiceImpl service;

    private Pagamento pagamento;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorPagamento validador = new ValidadorPagamento();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);

        pagamento = dadoPagamento();
    }

    @Test
    public void deveriaValidarPagamentoNuloAoSalvar() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("O pagamento não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoSalvar() {
        pagamento.setValor(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoSalvar() {
        pagamento.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoSalvar() {
        pagamento.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarFaturaNulaAoSalvar() {
        pagamento.setFatura(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("A fatura não pode ser vazia");
    }

    @Test
    public void deveriaValidarIdFaturaNuloAoSalvar() {
        pagamento.setFatura(new Fatura());
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("A fatura não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoNuloAoSalvar() {
        pagamento.setFormaPagamento(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("A forma de pagamento não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoCreditoAoSalvar() {
        pagamento.setFormaPagamento(CREDITO);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.salvar(pagamento)
        );

        assertThat(exception).hasMessage("Essa forma de pagamento é inválida");
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(null, pagamento)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarIdDiferenteAoAtualizar() {
        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(2, pagamento)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarPagamentoInexistenteAoAtualizar() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("Pagamento não encontrado");
    }

    @Test
    public void deveriaValidarPagamentoNuloAoAtualizar() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("O pagamento não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoAtualizar() {
        pagamento.setValor(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoAtualizar() {
        pagamento.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoAtualizar() {
        pagamento.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarFaturaNulaAoAtualizar() {
        pagamento.setFatura(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("A fatura não pode ser vazia");
    }

    @Test
    public void deveriaValidarIdFaturaNuloAoAtualizar() {
        pagamento.setFatura(new Fatura());
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("A fatura não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoNuloAoAtualizar() {
        pagamento.setFormaPagamento(null);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("A forma de pagamento não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoCreditoAoAtualizar() {
        pagamento.setFormaPagamento(CREDITO);
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.atualizar(1, pagamento)
        );

        assertThat(exception).hasMessage("Essa forma de pagamento é inválida");
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarPagamentoInexistenteAoRemover() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Pagamento não encontrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarPagamentoInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Pagamento não encontrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorFatura() {
        Exception exception = assertThrows(PagamentoException.class,
                () -> service.buscarPorFatura(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarPagamentoInexistenteAoBuscarPorFatura() {
        when(repository.buscarPorFatura(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<Pagamento> pagamentos = service.buscarPorFatura(1);
        assertThat(pagamentos).isEmpty();
    }

    @Test
    public void deveriaValidarPagamentoInexistenteAoBuscarTodos() {
        when(repository.buscarTodos(anyInt())).thenReturn(new ArrayList<>());
        List<Pagamento> pagamentos = service.buscarTodos();
        assertThat(pagamentos).isEmpty();
    }

    private Pagamento dadoPagamento() {
        return Pagamento.builder()
                .id(1)
                .descricao("Pagamento da fatura")
                .valor(BigDecimal.TEN)
                .fatura(new Fatura(1))
                .formaPagamento(FormaPagamento.BOLETO)
                .build();
    }
}
