package io.github.tibetteixeira.api.job;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura.ABERTO;
import static io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura.PAGO_PARCIALMENTE;

@Slf4j
@Component
@RequiredArgsConstructor
public class GerenciadorStatusFaturaJob {

    private final FaturaService faturaService;

    private static final String TODO_DIA_1_DA_MANHA = "0 0 1 * * *";

    @Scheduled(cron = TODO_DIA_1_DA_MANHA)
    public void processar() {
        log.info("Atualizando status da fatura");
        List<Fatura> faturas = faturaService.buscarPorStatus(ABERTO, PAGO_PARCIALMENTE);
        faturas.forEach(faturaService::atualizarStatusAtrasado);
    }
}
