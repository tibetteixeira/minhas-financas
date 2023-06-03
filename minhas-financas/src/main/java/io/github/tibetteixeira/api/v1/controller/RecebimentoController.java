package io.github.tibetteixeira.api.v1.controller;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.model.dto.RecebimentoDTO;
import io.github.tibetteixeira.api.v1.domain.service.RecebimentoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = Rotas.RECEBIMENTO)
public class RecebimentoController {

    private RecebimentoService service;

    @PostMapping(path = Rotas.EMPTY)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody RecebimentoDTO recebimento) {
        service.salvar(recebimento.toModel());
    }

    @PutMapping(path = Rotas.ID)
    public void atualizar(@PathVariable Integer id, @RequestBody RecebimentoDTO recebimento) {
        if (BooleanUtils.isFalse(id.equals(recebimento.getId())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        service.atualizar(id, recebimento.toModel());
    }

    @DeleteMapping(path = Rotas.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer id) {
        service.remover(id);
    }

    @GetMapping(path = Rotas.ID)
    public RecebimentoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).toDTO();
    }


    @GetMapping(path = Rotas.DESCRICAO)
    public List<RecebimentoDTO> buscarPorDescricao(@PathVariable String descricao) {
        return service.buscarPorDescricao(descricao).stream()
                .map(Recebimento::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = Rotas.EMPTY)
    public List<RecebimentoDTO> buscarTodas() {
        return service.buscarTodas().stream()
                .map(Recebimento::toDTO)
                .collect(Collectors.toList());
    }
}
