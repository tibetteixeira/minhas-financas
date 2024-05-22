package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CartaoService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorFatura;
import io.github.tibetteixeira.api.v1.exception.FaturaException;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FaturaServiceImplTest {
    @Mock
    private FaturaRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @Mock
    private CartaoService cartaoService;
    @Mock
    private Relogio relogio;
    @InjectMocks
    private FaturaServiceImpl service;

    private Fatura fatura;
    private Cartao cartao;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorFatura validador = new ValidadorFatura();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);
        when(usuarioLogado.getUsuario()).thenReturn(dadoUsuario());
        cartao = dadoCartao();
        fatura = dadoFatura();
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
    public void deveriaCriarFaturaDoCartaoPorMesAno() {
        when(repository.buscarPorCartaoMesAno(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(empty());
        when(repository.saveAndFlush(any(Fatura.class))).thenReturn(fatura);
        when(cartaoService.buscarPorId(anyInt())).thenReturn(cartao);

        service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2023, 7);

        verify(repository, times(1)).saveAndFlush(any(Fatura.class));
    }

    @Test
    public void deveriaBuscarFaturaDoCartaoPorMesAno() {
        when(repository.buscarPorCartaoMesAno(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(of(fatura));
        when(cartaoService.buscarPorId(anyInt())).thenReturn(cartao);

        Fatura faturaBuscada = service.criarOuBuscarFaturaDoCartaoPorMesAno(cartao, 2023, 7);

        assertThat(faturaBuscada).isNotNull();
        assertThat(faturaBuscada.getId()).isEqualByComparingTo(1);
        assertThat(faturaBuscada.getCartao().getId()).isEqualByComparingTo(1);
        verify(repository, times(0)).saveAndFlush(any(Fatura.class));
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

    @Test
    public void deveriaValidarAtualizacao() {
        FaturaDTO faturaDTO = new FaturaDTO(1, BigDecimal.ONE, BigDecimal.TEN);
        when(repository.buscarEstadoAtual(anyInt())).thenReturn(faturaDTO);

        service.atualizar(1, fatura);
        verify(repository, times(1)).save(any(Fatura.class));
    }

    @Test
    public void deveriaValidarStatusAbertoAoRealizarPagamento() {
        FaturaDTO faturaDTO = new FaturaDTO(1, BigDecimal.ZERO, BigDecimal.TEN);
        when(repository.buscarEstadoAtual(anyInt())).thenReturn(faturaDTO);

        service.atualizarStatus(fatura);
        assertThat(fatura.getStatus()).isEqualTo(ABERTO);
    }

    @Test
    public void deveriaValidarStatusPagoAoRealizarPagamento() {
        FaturaDTO faturaDTO = new FaturaDTO(1, BigDecimal.TEN, BigDecimal.TEN);
        when(repository.buscarEstadoAtual(anyInt())).thenReturn(faturaDTO);

        service.atualizarStatus(fatura);
        assertThat(fatura.getStatus()).isEqualTo(PAGO);
    }

    @Test
    public void deveriaValidarStatusPagoParcialmenteAoRealizarPagamento() {
        FaturaDTO faturaDTO = new FaturaDTO(1, BigDecimal.ONE, BigDecimal.TEN);
        when(repository.buscarEstadoAtual(anyInt())).thenReturn(faturaDTO);

        service.atualizarStatus(fatura);
        assertThat(fatura.getStatus()).isEqualTo(PAGO_PARCIALMENTE);
    }

    @Test
    public void deveriaValidarStatusAtrasadoAoRealizarPagamento() {
        FaturaDTO faturaDTO = new FaturaDTO(1, BigDecimal.ONE, BigDecimal.TEN);
        when(repository.buscarEstadoAtual(anyInt())).thenReturn(faturaDTO);

        fatura.setStatus(ATRASADO);
        service.atualizarStatus(fatura);
        assertThat(fatura.getStatus()).isEqualTo(ATRASADO);
    }

    @Test
    public void deveriaValidarAoAtualizarStatusAtrasado() {
        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, 7, 9, 1, 2));
        service.atualizarStatusAtrasado(fatura);
        assertThat(fatura.getStatus()).isEqualTo(ATRASADO);
        verify(repository, times(1)).save(any(Fatura.class));
    }

    @Test
    public void deveriaValidarAoAtualizarStatusNaoAtrasado() {
        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, 7, 7, 1, 2));
        service.atualizarStatusAtrasado(fatura);
        assertThat(fatura.getStatus()).isEqualTo(ABERTO);
        verify(repository, times(0)).save(any(Fatura.class));
    }

    private Fatura dadoFatura() {
        return Fatura.builder()
                .id(1)
                .cartao(cartao)
                .status(ABERTO)
                .usuario(usuarioLogado.getUsuario())
                .dataVencimento(LocalDate.of(2024, 7, cartao.getDiaVencimento()))
                .build();
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
