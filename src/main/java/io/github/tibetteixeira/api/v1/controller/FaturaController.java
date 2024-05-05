package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = Rotas.FATURA)
@RequiredArgsConstructor
public class FaturaController {

    private final FaturaService service;

    @GetMapping(path = Rotas.ID)
    public FaturaDTO carregarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }

    @GetMapping(path = Rotas.CARTAO_ID)
    public List<FaturaDTO> carregarPorCartao(@PathVariable Integer cartao) {
        return service.buscarPorCartao(cartao).stream()
                .map(Fatura::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<FaturaDTO> carregarTodas() {
        return service.buscarTodas().stream()
                .map(Fatura::toDTO)
                .toList();
    }

}
