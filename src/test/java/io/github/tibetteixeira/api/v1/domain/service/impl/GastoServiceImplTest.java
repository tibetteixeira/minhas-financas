package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.repository.GastoRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorGasto;
import io.github.tibetteixeira.api.v1.exception.GastoException;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento.CREDITO;
import static java.time.Month.APRIL;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class GastoServiceImplTest {

    @Mock
    private GastoRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @Mock
    private Relogio relogio;
    @InjectMocks
    private GastoServiceImpl service;

    private Gasto gasto;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorGasto validador = new ValidadorGasto();

        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);
        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, APRIL, 29, 20, 12));

        gasto = dadoGasto();
    }

    @Test
    public void deveriaValidarGastoNuloAoSalvar() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("O gasto não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoSalvar() {
        gasto.setValor(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoSalvar() {
        gasto.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoSalvar() {
        gasto.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarDescricaoVaziaAoSalvar() {
        gasto.setDescricao(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarDataNulaAoSalvar() {
        gasto.setDataGasto(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarDataInvalidaAoSalvar() {
        gasto.setDataGasto(LocalDateTime.of(2009, 12, 31, 23, 59));
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("A data não pode ser anterior a 2010");
    }

    @Test
    public void deveriaValidarFormaPagamentoNuloAoSalvar() {
        gasto.setFormaPagamento(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("A forma de pagamento não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoCreditoSemCartaoAoSalvar() {
        gasto.setFormaPagamento(CREDITO);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("O gasto por crédito deve ter algum cartão associado");
    }

    @Test
    public void deveriaValidarCategoriaNulaAoSalvar() {
        gasto.setCategoria(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.salvar(gasto)
        );

        assertThat(exception).hasMessage("A categoria não pode ser vazia");
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(null, gasto)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarIdIncorretoAoAtualizar() {
        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(2, gasto)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarGastoNuloAoAtualizar() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("O gasto não pode ser vazio");
    }

    @Test
    public void deveriaValidarGastoInexistenteAoAtualizar() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("Gasto não encontrado");
    }

    @Test
    public void deveriaValidarValorNuloAoAtualizar() {
        gasto.setValor(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNegativoAoAtualizar() {
        gasto.setValor(BigDecimal.TEN.negate());
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarValorZeradoAoAtualizar() {
        gasto.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("O valor não pode ser zero ou negativo");
    }

    @Test
    public void deveriaValidarDescricaoVaziaAoAtualizar() {
        gasto.setDescricao(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarDataNulaAoAtualizar() {
        gasto.setDataGasto(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarDataInvalidaAoAtualizar() {
        gasto.setDataGasto(LocalDateTime.of(2009, 2, 2, 20, 21));
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("A data não pode ser anterior a 2010");
    }

    @Test
    public void deveriaValidarFormaPagamentoNuloAoAtualizar() {
        gasto.setFormaPagamento(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("A forma de pagamento não pode ser vazia");
    }

    @Test
    public void deveriaValidarFormaPagamentoCreditoSemCartaoAoAtualizar() {
        gasto.setFormaPagamento(CREDITO);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("O gasto por crédito deve ter algum cartão associado");
    }

    @Test
    public void deveriaValidarCategoriaNulaAoAtualizar() {
        gasto.setCategoria(null);
        Exception exception = assertThrows(GastoException.class,
                () -> service.atualizar(1, gasto)
        );

        assertThat(exception).hasMessage("A categoria não pode ser vazia");
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarGastoInexistenteAoRemover() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Gasto não encontrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarGastoInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Gasto não encontrado");
    }

    @Test
    public void deveriaValidarCategoriaNulaAoBuscarPorCategoria() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorCategoria(null)
        );

        assertThat(exception).hasMessage("O ID não pode ser vazio");
    }

    @Test
    public void deveriaValidarGastoInexistenteAoBuscarPorCategoria() {
        when(repository.buscarPorCategoria(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<Gasto> gastos = service.buscarPorCategoria(1);
        assertThat(gastos).isEmpty();
    }

    @Test
    public void deveriaValidarGastoInexistenteAoBuscarTodos() {
        when(repository.buscarTodos(anyInt())).thenReturn(new ArrayList<>());
        List<Gasto> gastos = service.buscarTodos();
        assertThat(gastos).isEmpty();
    }

    @Test
    public void deveriaValidarAnoNuloAoBuscarPorData() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorData(null, Mes.ABRIL)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarMesNuloAoBuscarPorData() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorData(2024, null)
        );

        assertThat(exception).hasMessage("A data não pode ser vazia");
    }

    @Test
    public void deveriaValidarDataInvalidaAoBuscarPorData() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorData(2009, Mes.ABRIL)
        );

        assertThat(exception).hasMessage("A data não pode ser anterior a 2010");
    }

    @Test
    public void deveriaValidarGastoInexistenteAoBuscarPorData() {
        when(repository.buscarPorData(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<Gasto> gastos = service.buscarPorData(2024, Mes.ABRIL);
        assertThat(gastos).isEmpty();
    }

    private Gasto dadoGasto() {
        return Gasto.builder()
                .id(1)
                .descricao("Meu gasto")
                .formaPagamento(FormaPagamento.DINHEIRO)
                .valor(BigDecimal.TEN)
                .categoria(new CategoriaGasto(2))
                .dataGasto(relogio.hoje())
                .build();
    }

}
