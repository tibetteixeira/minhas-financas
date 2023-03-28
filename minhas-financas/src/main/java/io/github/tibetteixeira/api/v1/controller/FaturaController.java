package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.FATURA)
@AllArgsConstructor
public class FaturaController {

    private FaturaService service;

    @GetMapping(path = Rotas.ID)
    public FaturaDTO carregarFatura(@PathVariable Integer id) {
        Fatura fatura = service.buscarPorId(id);
        FaturaDTO faturaDTO = fatura.toDTO();
        faturaDTO.setGastos(fatura.getGastos().stream()
                .peek(gasto -> gasto.setFatura(null))
                .map(Gasto::toDTO)
                .collect(Collectors.toList()));
        return faturaDTO;
    }

    @GetMapping(path = Rotas.CARTAO_ID)
    public List<FaturaDTO> carregarFaturasDoCartao(@PathVariable Integer cartao) {
        return service.buscarFaturaPorCartao(cartao).stream()
                .map(fatura -> {
                    FaturaDTO faturaDTO = fatura.toDTO();
                    faturaDTO.setGastos(fatura.getGastos().stream()
                            .peek(gasto -> gasto.setFatura(null))
                            .map(Gasto::toDTO)
                            .collect(Collectors.toList()));
                    return faturaDTO;
                })
                .collect(Collectors.toList());
    }
}
