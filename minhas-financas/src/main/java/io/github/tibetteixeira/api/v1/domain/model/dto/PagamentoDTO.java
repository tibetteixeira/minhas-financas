package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {

    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private Date dataPagamento;
    private TipoPagamento tipoPagamento;
    private GastoDTO gasto;
    private FaturaDTO fatura;

    public Pagamento toModel() {
        Pagamento pagamento = new Pagamento();

        pagamento.setId(id);
        pagamento.setValor(valor);
        pagamento.setDescricao(descricao);
        pagamento.setDataPagamento(dataPagamento);
        pagamento.setTipoPagamento(tipoPagamento);
        pagamento.setGasto(gasto.toModel());
        pagamento.setFatura(fatura.toModel());

        return pagamento;
    }
}
