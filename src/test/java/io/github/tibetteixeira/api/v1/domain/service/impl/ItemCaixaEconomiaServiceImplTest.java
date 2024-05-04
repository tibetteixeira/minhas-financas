package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.repository.CaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.repository.ItemCaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.ItemCaixaEconomiaException;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.Month.APRIL;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ItemCaixaEconomiaServiceImplTest {
    @Mock
    private ItemCaixaEconomiaRepository repository;
    @Mock
    private UsuarioLogado usuarioLogado;
    @Mock
    private Relogio relogio;
    @Mock
    private ValidadorCaixaEconomia validadorCaixa;
    @Mock
    private CaixaEconomiaRepository caixaRepository;
    @InjectMocks
    private ItemCaixaEconomiaServiceImpl service;

    private ItemCaixaEconomia item;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorItemCaixaEconomia validador = new ValidadorItemCaixaEconomia();
        final CaixaEconomiaService caixaService = new CaixaEconomiaServiceImpl(caixaRepository, relogio, usuarioLogado, validadorCaixa);

        ReflectionTestUtils.setField(service, "validador", validador);
        ReflectionTestUtils.setField(service, "caixaService", caixaService);

        when(usuarioLogado.getId()).thenReturn(1);
        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, APRIL, 29, 20, 12));

        item = dadoItemPositivo();
    }

    @Test
    public void deveriaValidarItemNuloAoSalvar() {
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorNuloAoSalvar() {
        item.setValor(null);
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.salvar(item)
        );

        assertThat(exception).hasMessage("Item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarValorZeroAoSalvar() {
        item.setValor(BigDecimal.ZERO);
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.salvar(item)
        );

        assertThat(exception).hasMessage("Item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaNuloAoSalvar() {
        item.setCaixa(null);
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarIdCaixaNuloAoSalvar() {
        item.setCaixa(new CaixaEconomia());
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoSalvar() {
        when(caixaRepository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.salvar(item)
        );

        assertThat(exception).hasMessage("Caixinha de economia não encontrada");
    }

    @Test
    public void deveriaValidarValorPositivoENegativoAoSalvar() {
        CaixaEconomia caixa = dadoCaixa();

        when(caixaRepository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(ofNullable(caixa));

        service.salvar(dadoItemPositivo());
        service.salvar(dadoItemNegativo());

        assertThat(item).isNotNull();
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("Id do item da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarItemInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Item da caixinha de economia não encontrado");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoBuscarPorId() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Item da caixinha de economia não encontrado");
    }

    @Test
    public void deveriaValidarIdCaixaNuloAoBuscarPorCaixa() {
        Exception exception = assertThrows(ItemCaixaEconomiaException.class,
                () -> service.buscarPorCaixaEconomia(null)
        );

        assertThat(exception).hasMessage("Id da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoBuscarPorCaixa() {
        when(repository.buscarPorId(anyInt(), anyInt())).thenReturn(empty());
        assertThat(service.buscarPorCaixaEconomia(1)).isEmpty();
    }

    private ItemCaixaEconomia dadoItemPositivo() {
        return ItemCaixaEconomia.builder()
                .id(1)
                .valor(BigDecimal.TEN)
                .dataEconomia(relogio.hoje())
                .caixa(dadoCaixa())
                .build();
    }

    private ItemCaixaEconomia dadoItemNegativo() {
        return ItemCaixaEconomia.builder()
                .id(1)
                .valor(BigDecimal.TEN.negate())
                .dataEconomia(relogio.hoje())
                .caixa(dadoCaixa())
                .build();
    }

    private CaixaEconomia dadoCaixa() {
        return CaixaEconomia.builder()
                .id(1)
                .nome("Minha caixinha")
                .descricao("Caixinha de economia")
                .prazo(12)
                .valorObjetivo(BigDecimal.TEN)
                .dataCriacao(relogio.hoje())
                .build();
    }
}
