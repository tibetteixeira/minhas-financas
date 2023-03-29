package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
    private Integer usuarioId;
    private Integer faturaId;

    public Pagamento toModel() {
        Pagamento pagamento = new Pagamento();

        pagamento.setId(id);
        pagamento.setValor(valor);
        pagamento.setDescricao(descricao);
        pagamento.setDataPagamento(dataPagamento);
        pagamento.setTipoPagamento(tipoPagamento);
        pagamento.setUsuario(new Usuario(usuarioId));
        pagamento.setFatura(Objects.nonNull(faturaId) ? new Fatura(faturaId) : null);

        return pagamento;
    }
}
