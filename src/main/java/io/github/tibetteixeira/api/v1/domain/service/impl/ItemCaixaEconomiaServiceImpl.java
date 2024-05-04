package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.repository.ItemCaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import io.github.tibetteixeira.api.v1.domain.service.ItemCaixaEconomiaService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCaixaEconomiaServiceImpl implements ItemCaixaEconomiaService {

    private final ItemCaixaEconomiaRepository repository;
    private final ValidadorItemCaixaEconomia validador;
    private final Relogio relogio;
    private final UsuarioLogado usuarioLogado;
    private final CaixaEconomiaService caixaService;

    @Override
    public void salvar(ItemCaixaEconomia itemCaixaEconomia) {
        validador.validar(itemCaixaEconomia);
        caixaService.validar(itemCaixaEconomia.getCaixa().getId());
        itemCaixaEconomia.setDataEconomia(relogio.hoje());
        repository.save(itemCaixaEconomia);
    }

    @Override
    public void atualizar(Integer id, ItemCaixaEconomia itemCaixaEconomia) {}

    @Override
    public void remover(Integer id) {}

    @Override
    public ItemCaixaEconomia buscarPorId(Integer id) {
        validador.validarId(id);
        return repository.buscarPorId(id, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.ITEM_CAIXINHA_ECONOMIA_NAO_ENCONTRADO));
    }

    @Override
    public List<ItemCaixaEconomia> buscarPorCaixaEconomia(Integer caixaId) {
        validador.validarCaixa(caixaId);
        return repository.buscarPorCaixaEconomia(caixaId, usuarioLogado.getId());
    }
}
