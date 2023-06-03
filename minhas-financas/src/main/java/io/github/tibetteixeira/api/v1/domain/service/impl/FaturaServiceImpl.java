package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.exception.FaturaException;
import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.repository.FaturaRepository;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new FaturaException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        buscarPorId(id);

        repository.save(fatura);
    }

    @Override
    public void remover(Integer id) {
        // Não terá remoção de fatura
    }

    @Override
    public Fatura buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionMessage.FATURA_NAO_ENCONTRADA));
    }

    @Override
    public List<Fatura> buscarFaturaPorCartao(Integer cartaoId) {
        List<Fatura> faturas = repository.findByCartaoOrderByDataVencimentoDesc(new Cartao(cartaoId));

        if (listaNaoValida(faturas))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionMessage.NAO_EXISTE_FATURA_PARA_ESSE_CARTAO);

        return faturas;
    }

    @Override
    public Fatura buscarFaturaDoCartaoPorMesAno(Fatura fatura) {
        return repository.findByCartaoAndMesAndAno(fatura.getCartao(), fatura.getMes(), fatura.getAno());
    }

    @Override
    public Fatura buscaOuSalva(Fatura fatura) {
        Fatura faturaDaBase = buscarPorId(fatura.getId());
        return Objects.isNull(faturaDaBase) ? repository.saveAndFlush(fatura) : faturaDaBase;

    }
}
