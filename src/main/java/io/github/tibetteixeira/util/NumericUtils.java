package io.github.tibetteixeira.util;

import java.math.BigDecimal;
import java.util.Objects;

public class NumericUtils {

    private NumericUtils()  {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal zeroIfNull(BigDecimal valor) {
        return Objects.nonNull(valor) ? valor : BigDecimal.ZERO;
    }

    public static Integer zeroIfNull(Integer valor) {
        return Objects.nonNull(valor) ? valor : 0;
    }
}
