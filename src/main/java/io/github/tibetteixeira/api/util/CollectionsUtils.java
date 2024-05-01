package io.github.tibetteixeira.api.util;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class CollectionsUtils {

    private CollectionsUtils() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("rawtypes")
    public static boolean listaValida(List lista) {
        return nonNull(lista) && isFalse(lista.isEmpty());
    }

    @SuppressWarnings("rawtypes")
    public static boolean listaNaoValida(List lista) {
        return isFalse(listaValida(lista));
    }
}
