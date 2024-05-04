package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.repository.CaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.CaixaEconomiaException;
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
import java.util.Optional;

import static java.time.Month.APRIL;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CaixaEconomiaServiceImplTest {

    @Mock
    private CaixaEconomiaRepository repository;

    @Mock
    private UsuarioLogado usuarioLogado;

    @Mock
    private Relogio relogio;

    @InjectMocks
    private CaixaEconomiaServiceImpl service;

    private CaixaEconomia caixa;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorCaixaEconomia validador = new ValidadorCaixaEconomia();
        ReflectionTestUtils.setField(service, "validador", validador);

        when(usuarioLogado.getId()).thenReturn(1);
        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, APRIL, 29, 20, 12));

        caixa = dadoCaixa();
    }

    @Test
    public void deveriaValidarCaixaNuloAoSalvar() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarNomeVazioAoSalvar() {
        caixa.setNome("");

        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.salvar(caixa)
        );

        assertThat(exception).hasMessage("Nome da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarApenasCaixaENomeAoSalvar() {
        caixa.setDescricao(null);
        caixa.setValorObjetivo(null);
        caixa.setValorEconomizado(null);
        caixa.setPrazo(null);
        caixa.setDataCriacao(null);
        caixa.setUsuario(null);
        caixa.setItens(null);

        service.salvar(caixa);
        assertThat(caixa).isNotNull();
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.atualizar(null, caixa)
        );

        assertThat(exception).hasMessage("Id da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaNuloAoAtualizar() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("Caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarNomeVazioAoAtualizar() {
        caixa.setNome("");

        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.atualizar(1, caixa)
        );

        assertThat(exception).hasMessage("Nome da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarApenasCaixaNomeEIdAoAtualizar() {
        when(repository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(Optional.ofNullable(caixa));
        caixa.setDescricao(null);
        caixa.setValorObjetivo(null);
        caixa.setValorEconomizado(null);
        caixa.setPrazo(null);
        caixa.setDataCriacao(null);
        caixa.setUsuario(null);
        caixa.setItens(null);

        service.atualizar(1, caixa);

        assertThat(caixa).isNotNull();
    }

    @Test
    public void deveriaValidarIdIncorretoAoAtualizar() {
        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(2, caixa)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoAtualizar() {
        when(repository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> service.atualizar(1, caixa)
        );

        assertThat(exception).hasMessage("Caixinha de economia não encontrada");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("Id da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoBuscarPorId() {
        when(repository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Caixinha de economia não encontrada");
    }

    @Test
    public void deveriaValidarNomeVazioAoBuscarPorNome() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.buscarPorNome("")
        );

        assertThat(exception).hasMessage("Nome da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoBuscarPorNome() {
        when(repository.buscarPorNomeEUsuario(anyString(), anyInt())).thenReturn(new ArrayList<>());

        List<CaixaEconomia> caixinhas = service.buscarPorNome("caixa");

        assertThat(caixinhas).isEmpty();
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoBuscarTodas() {
        when(repository.buscarPorUsuario(anyInt())).thenReturn(new ArrayList<>());

        List<CaixaEconomia> caixinhas = service.buscarTodas();

        assertThat(caixinhas).isEmpty();
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("Id da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoRemover() {
        when(repository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Caixinha de economia não encontrada");
    }

    @Test
    public void deveriaValidarIdNuloAoValidarCaixa() {
        Exception exception = assertThrows(CaixaEconomiaException.class,
                () -> service.validar(null)
        );

        assertThat(exception).hasMessage("Id da caixinha não pode ser vazio");
    }

    @Test
    public void deveriaValidarCaixaInexistenteAoValidarCaixa() {
        when(repository.buscarPorIdEUsuario(anyInt(), anyInt())).thenReturn(empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> service.validar(1)
        );

        assertThat(exception).hasMessage("Caixinha de economia não encontrada");
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
