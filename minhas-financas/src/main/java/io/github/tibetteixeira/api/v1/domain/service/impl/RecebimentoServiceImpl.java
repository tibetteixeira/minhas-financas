package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.repository.RecebimentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.RecebimentoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class RecebimentoServiceImpl implements RecebimentoService {

    private RecebimentoRepository repository;

    @Override
    public void salvar(Recebimento recebimento) {
        repository.save(recebimento);
    }

    @Override
    public void atualizar(Integer id, Recebimento recebimento) {
        Recebimento recebimentoDaBase = buscarPorId(id);

        recebimentoDaBase.setDescricao(recebimento.getDescricao());
        recebimentoDaBase.setDataRecebimento(recebimento.getDataRecebimento());
        recebimentoDaBase.setTipoRecebimento(recebimento.getTipoRecebimento());
        recebimentoDaBase.setValor(recebimento.getValor());

        repository.save(recebimentoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Recebimento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionMessage.RECEBIMENTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Recebimento> buscarPorDescricao(String descricao) {
        return repository.findByDescricaoContainsIgnoreCaseOrderByDataRecebimentoDesc(descricao);
    }

    @Override
    public List<Recebimento> buscarTodas() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataRecebimento"));
    }

}
