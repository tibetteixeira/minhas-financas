package io.github.tibetteixeira.api.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionsUtilsTest {

    @Test
    public void deveriaValidarListaValidaParaListaNula() {
        boolean resultado = CollectionsUtils.listaValida(null);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarListaValidaParaListaVazia() {
        boolean resultado = CollectionsUtils.listaValida(new ArrayList<>());
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarListaValidaParaListaComItens() {
        boolean resultado = CollectionsUtils.listaValida(List.of(1));
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarListaNaoValidaParaListaNula() {
        boolean resultado = CollectionsUtils.listaNaoValida(null);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarListaNaoValidaParaListaVazia() {
        boolean resultado = CollectionsUtils.listaNaoValida(new ArrayList<>());
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarListaNaoValidaParaListaComItens() {
        boolean resultado = CollectionsUtils.listaNaoValida(List.of(1));
        assertThat(resultado).isFalse();
    }


}
