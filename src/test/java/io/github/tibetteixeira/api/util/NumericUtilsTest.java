package io.github.tibetteixeira.api.util;

import org.junit.Test;

import java.math.BigDecimal;

import static io.github.tibetteixeira.api.util.NumericUtils.*;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

public class NumericUtilsTest {

    @Test
    public void deveriaValidarZeroIfNullParaBigDecimalNull() {
        BigDecimal resultado = zeroIfNull((BigDecimal) null);
        assertThat(resultado).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void deveriaValidarZeroIfNullParaBigDecimalZero() {
        BigDecimal resultado = zeroIfNull(BigDecimal.ZERO);
        assertThat(resultado).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void deveriaValidarZeroIfNullParaBigDecimalDiferenteDeZero() {
        BigDecimal resultado = zeroIfNull(BigDecimal.TEN);
        assertThat(resultado).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    public void deveriaValidarZeroIfNullParaIntegerNull() {
        Integer resultado = zeroIfNull((Integer) null);
        assertThat(resultado).isEqualByComparingTo(0);
    }

    @Test
    public void deveriaValidarZeroIfNullParaIntegerZero() {
        Integer resultado = zeroIfNull(0);
        assertThat(resultado).isEqualByComparingTo(0);
    }

    @Test
    public void deveriaValidarZeroIfNullParaIntegerDiferenteDeZero() {
        Integer resultado = zeroIfNull(1);
        assertThat(resultado).isEqualByComparingTo(1);
    }

    @Test
    public void deveriaValidarMaiorQueParaValor1MaiorQueValor2() {
        boolean resultado = maiorQue(ONE, ZERO);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMaiorQueParaValor1MenorQueValor2() {
        boolean resultado = maiorQue(ZERO, ONE);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarMaiorQueParaValor1IgualQueValor2() {
        boolean resultado = maiorQue(ONE, ONE);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarMenorQueParaValor1MaiorQueValor2() {
        boolean resultado = menorQue(ONE, ZERO);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarMenorQueParaValor1MenorQueValor2() {
        boolean resultado = menorQue(ZERO, ONE);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMenorQueParaValor1IgualQueValor2() {
        boolean resultado = menorQue(ONE, ONE);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarIgualAParaValor1MaiorQueValor2() {
        boolean resultado = igualA(ONE, ZERO);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarIgualAParaValor1MenorQueValor2() {
        boolean resultado = igualA(ZERO, ONE);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarIgualAParaValor1IgualQueValor2() {
        boolean resultado = igualA(ONE, ONE);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMaiorQueOuIgualAParaValor1MaiorQueValor2() {
        boolean resultado = maiorQueOuIgualA(ONE, ZERO);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMaiorQueOuIgualAParaValor1MenorQueValor2() {
        boolean resultado = maiorQueOuIgualA(ZERO, ONE);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarMaiorQueOuIgualAParaValor1IgualQueValor2() {
        boolean resultado = maiorQueOuIgualA(ONE, ONE);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMenorQueOuIgualAParaValor1MaiorQueValor2() {
        boolean resultado = menorQueOuIgualA(ONE, ZERO);
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveriaValidarMenorQueOuIgualAParaValor1MenorQueValor2() {
        boolean resultado = menorQueOuIgualA(ZERO, ONE);
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveriaValidarMenorQueOuIgualAParaValor1IgualQueValor2() {
        boolean resultado = menorQueOuIgualA(ONE, ONE);
        assertThat(resultado).isTrue();
    }

}
