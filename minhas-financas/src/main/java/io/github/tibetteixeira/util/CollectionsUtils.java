package io.github.tibetteixeira.util;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class CollectionsUtils {

    public static boolean listaValida(List lista) {
        return nonNull(lista) && isFalse(lista.isEmpty());
    }

    public static boolean listaNaoValida(List lista) {
        return isFalse(listaValida(lista));
    }
}
