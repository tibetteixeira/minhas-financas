package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.util.UsuarioLogado;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.repository.GastoRepository;
import io.github.tibetteixeira.api.v1.domain.service.FaturaService;
import io.github.tibetteixeira.api.v1.domain.service.GastoService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorGasto;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.tibetteixeira.api.v1.domain.model.enums.FormaPagamento.CREDITO;

@Service
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    private final GastoRepository repository;
    private final FaturaService faturaService;
    private final ValidadorGasto validador;
    private final UsuarioLogado usuarioLogado;

    @Override
    public void salvar(Gasto gasto) {
        validador.validar(gasto);

        if (CREDITO.equals(gasto.getFormaPagamento())) {
            Fatura fatura = faturaService.criarOuBuscarFaturaDoCartaoPorMesAno(gasto.getCartao(),
                    gasto.getDataGasto().getYear(),
                    gasto.getDataGasto().getMonthValue());
            gasto.setFatura(fatura);
        }

        gasto.setUsuario(usuarioLogado.getUsuario());

        repository.save(gasto);
    }

    @Override
    public void atualizar(Integer id, Gasto gasto) {
        validador.validar(id, gasto);

        Gasto gastoDaBase = buscarPorId(id);
        gastoDaBase.setDescricao(gasto.getDescricao());
        gastoDaBase.setDataGasto(gasto.getDataGasto());
        gastoDaBase.setCategoria(gasto.getCategoria());
        gastoDaBase.setFormaPagamento(gasto.getFormaPagamento());
        gastoDaBase.setValor(gasto.getValor());

        if (CREDITO.equals(gasto.getFormaPagamento())) {
            Fatura fatura = faturaService.criarOuBuscarFaturaDoCartaoPorMesAno(gasto.getCartao(),
                    gasto.getDataGasto().getYear(),
                    gasto.getDataGasto().getMonthValue());
            gastoDaBase.setFatura(fatura);
        } else {
            gastoDaBase.setFatura(null);
        }

        repository.save(gastoDaBase);
    }

    @Override
    public void remover(Integer id) {
        repository.delete(buscarPorId(id));
    }

    @Override
    public Gasto buscarPorId(Integer id) {
        validador.validar(id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.GASTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Gasto> buscarPorCategoria(Integer categoriaId) {
        validador.validar(categoriaId);
        return repository.buscarPorCategoria(categoriaId, usuarioLogado.getId());
    }

    @Override
    public List<Gasto> buscarTodos() {
        return repository.buscarTodos(usuarioLogado.getId());
    }

    @Override
    public List<Gasto> buscarPorData(Integer ano, Mes mes) {
        validador.validarAno(ano);
        validador.validarMes(mes);
        return repository.buscarPorData(ano, mes.getNumeroMes(), usuarioLogado.getId());
    }
}
