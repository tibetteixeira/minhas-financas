package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.FATURA)
public class FaturaController {

    private FaturaService service;

    public FaturaController(FaturaService service) {
        this.service = service;
    }

    @GetMapping(path = Rotas.ID)
    public FaturaDTO carregarFatura(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.CARTAO_ID)
    public List<FaturaDTO> carregarFaturasDoCartao(@PathVariable Integer cartao) {
        return service.buscarFaturaPorCartao(cartao).stream()
                .map(Fatura::toDTO)
                .collect(Collectors.toList());
    }
}
