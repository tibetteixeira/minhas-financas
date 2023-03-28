package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.FaturaException;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.github.tibetteixeira.util.CollectionsUtils.listaNaoValida;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@AllArgsConstructor
public class FaturaServiceImpl implements FaturaService {

    private FaturaRepository repository;

    @Override
    public void salvar(Fatura fatura) {
        repository.save(fatura);
    }

    @Override
    public void atualizar(Integer id, Fatura fatura) {
        if (isFalse(id.equals(fatura.getId())))
            throw new FaturaException("Id da fatura diferente do id da Url.");

        buscarPorId(id);

        repository.save(fatura);
    }

    @Override
    public void remover(Integer id) {
    }

    @Override
    public Fatura buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new FaturaException("Fatura não encontrada."));
    }

    @Override
    public List<Fatura> buscarFaturaPorCartao(Integer cartaoId) {
        List<Fatura> faturas = repository.findByCartao(new Cartao(cartaoId));

        if (listaNaoValida(faturas))
            throw new FaturaException("Não existe fatura para esse cartão.");

        return faturas;
    }

    @Override
    public Fatura buscarFaturaDoCartaoPorMesAno(Fatura fatura) {
        return repository.findByCartaoAndMesAndAno(fatura.getCartao(), fatura.getMes(), fatura.getAno());
    }

    @Override
    public Fatura buscaOuSalva(Fatura fatura) {
        Fatura faturaDaBase = buscarFaturaDoCartaoPorMesAno(fatura);
        return Objects.isNull(faturaDaBase) ? repository.saveAndFlush(fatura) : faturaDaBase;

    }
}
