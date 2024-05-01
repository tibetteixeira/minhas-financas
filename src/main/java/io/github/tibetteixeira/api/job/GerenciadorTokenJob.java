package io.github.tibetteixeira.api.job;

import io.github.tibetteixeira.api.configuration.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GerenciadorTokenJob {

    private final TokenService tokenService;

    private static final int UM_MINUTO = 60 * 1000;
    private static final int UMA_HORA = 60 * UM_MINUTO;

    @Scheduled(fixedDelay = UMA_HORA, initialDelay = UM_MINUTO)
    public void processar() {
        log.info("Inativando tokens inválidos");
        tokenService.inativarTokensInvalidos();
    }
}
