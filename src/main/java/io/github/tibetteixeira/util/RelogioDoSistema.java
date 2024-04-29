package io.github.tibetteixeira.util;

import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RelogioDoSistema implements Relogio {

    @Override
    public LocalDateTime hoje() {
        return LocalDateTime.now();
    }
}
