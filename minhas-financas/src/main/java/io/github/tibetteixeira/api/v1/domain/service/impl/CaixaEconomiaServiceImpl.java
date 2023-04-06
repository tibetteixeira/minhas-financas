package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.CaixaEconomiaException;
import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import io.github.tibetteixeira.api.v1.domain.repository.CaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.repository.ItemCaixaEconomiaRepository;
import io.github.tibetteixeira.api.v1.domain.service.CaixaEconomiaService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CaixaEconomiaServiceImpl implements CaixaEconomiaService {

    private CaixaEconomiaRepository repository;
    private ItemCaixaEconomiaRepository itemCaixaEconomiaRepository;

    @Override
    public void salvar(CaixaEconomia caixaEconomia) {
        caixaEconomia.setDataCriacao(new Date());
        repository.save(caixaEconomia);
    }

    @Override
    public void atualizar(Integer id, CaixaEconomia caixaEconomia) {
        CaixaEconomia caixaEconomiaDaBase = buscarPorId(id);

        caixaEconomiaDaBase.setNome(caixaEconomia.getNome());
        caixaEconomiaDaBase.setPrazo(caixaEconomia.getPrazo());
        caixaEconomiaDaBase.setDescricao(caixaEconomia.getDescricao());
        caixaEconomiaDaBase.setValorEconomizado(caixaEconomia.getValorEconomizado());
        caixaEconomiaDaBase.setValorObjetivo(caixaEconomia.getValorObjetivo());
        caixaEconomiaDaBase.setItens(caixaEconomia.getItens());

        repository.save(caixaEconomiaDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public CaixaEconomia buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CaixaEconomiaException("Caixinha de economia não encontrada."));
    }

    @Override
    public List<CaixaEconomia> buscarPorNome(String nome) {
        return repository.findByNomeContainsIgnoreCaseOrderByNome(nome);
    }

    @Override
    public List<CaixaEconomia> buscarTodas() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public void salvar(ItemCaixaEconomia itemCaixaEconomia) {
        itemCaixaEconomia.setDataEconomia(new Date());
        itemCaixaEconomiaRepository.save(itemCaixaEconomia);
    }

    @Override
    public void atualizar(Integer id, ItemCaixaEconomia itemCaixaEconomia) {
        ItemCaixaEconomia itemCaixaEconomiaDaBase = buscarItemPorId(id);

        if (BooleanUtils.isFalse(itemCaixaEconomia.getValor().equals(itemCaixaEconomiaDaBase.getValor()))) {
            itemCaixaEconomiaDaBase.setValor(itemCaixaEconomia.getValor());
            itemCaixaEconomiaDaBase.setDataEconomia(new Date());
            itemCaixaEconomiaRepository.save(itemCaixaEconomiaDaBase);
        }
    }

    @Override
    public ItemCaixaEconomia buscarItemPorId(Integer id) {
        return itemCaixaEconomiaRepository.findById(id)
                .orElseThrow(() -> new CaixaEconomiaException("Item da caixinha de economia não encontrado."));
    }

    @Override
    public void removerItem(Integer id) {
        itemCaixaEconomiaRepository.delete(buscarItemPorId(id));
    }
}
