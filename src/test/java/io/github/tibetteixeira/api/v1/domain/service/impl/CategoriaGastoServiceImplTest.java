package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.repository.CategoriaGastoRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorCategoriaGasto;
import io.github.tibetteixeira.api.v1.exception.GastoException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoriaGastoServiceImplTest {

    @Mock
    private CategoriaGastoRepository repository;
    @InjectMocks
    private CategoriaGastoServiceImpl service;

    private CategoriaGasto categoria;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorCategoriaGasto validador = new ValidadorCategoriaGasto();
        ReflectionTestUtils.setField(service, "validador", validador);

        categoria = dadoCategoriaGasto();
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("Id da categoria de gasto não pode ser vazio");
    }

    @Test
    public void deveriaValidarCategoriaInexistenteAoBuscarPorId() {
        when(repository.findById(anyInt())).thenReturn(empty());
        Exception exception = assertThrows(NotFoundException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Categoria não encontrada");
    }

    @Test
    public void deveriaValidarDescricaoVaziaAoBuscarPorDescricao() {
        Exception exception = assertThrows(GastoException.class,
                () -> service.buscarCategoriaPorDescricao("")
        );

        assertThat(exception).hasMessage("Descrição não pode ser vazia");
    }

    @Test
    public void deveriaValidarRetornoVazioAoBuscarPorDescricao() {
        when(repository.findByDescricaoContainsIgnoreCaseOrderByDescricao(anyString())).thenReturn(new ArrayList<>());
        List<CategoriaGasto> categorias = service.buscarCategoriaPorDescricao("123");
        assertThat(categorias).isEmpty();
    }

    private CategoriaGasto dadoCategoriaGasto() {
        return CategoriaGasto.builder()
                .id(1)
                .descricao("Descrição")
                .build();
    }
}
