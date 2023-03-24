package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.RecebimentoException;
import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import io.github.tibetteixeira.api.v1.domain.repository.RecebimentoRepository;
import io.github.tibetteixeira.api.v1.domain.service.CategoriaRecebimentoService;
import io.github.tibetteixeira.api.v1.domain.service.RecebimentoService;
import io.github.tibetteixeira.util.CollectionsUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecebimentoServiceImpl implements RecebimentoService {

    private RecebimentoRepository repository;
    private CategoriaRecebimentoService categoriaRecebimentoService;

    @Override
    public void salvar(Recebimento recebimento) {
        repository.save(recebimento);
    }

    @Override
    public void atualizar(Integer id, Recebimento recebimento) {
        Recebimento recebimentoDaBase = buscarPorId(id);

        recebimentoDaBase.setDescricao(recebimento.getDescricao());
        recebimentoDaBase.setDataRecebimento(recebimento.getDataRecebimento());
        recebimentoDaBase.setCategoria(recebimento.getCategoria());
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
                .orElseThrow(() -> new RecebimentoException("Recebimento não encontrado."));
    }

    @Override
    public List<Recebimento> buscarRecebimentoPorCategoria(Integer idCategoria) {
        CategoriaRecebimento categoria = categoriaRecebimentoService.buscarPorId(idCategoria);
        List<Recebimento> recebimentos = repository.findByCategoria(categoria);

        if (CollectionsUtils.listaValida(recebimentos))
            return recebimentos;

        throw new RecebimentoException("Não há recebimentos para essa categoria.");
    }

    @Override
    public List<Recebimento> buscarTodas() {
        return repository.findAll();
    }

}
