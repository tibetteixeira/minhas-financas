package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FaturaDTO {

    private Integer id;
    private Date dataVencimento;
    private StatusPagamentoFatura status;
    private BigDecimal valorPago;
    private CartaoDTO cartao;
    private Mes mes;
    private Integer ano;

    public Fatura toModel() {
        Fatura fatura = new Fatura();

        fatura.setId(id);
        fatura.setDataVencimento(dataVencimento);
        fatura.setStatus(status);
        fatura.setValorPago(valorPago);
        fatura.setCartao(cartao.toModel());
        fatura.setMes(mes);
        fatura.setAno(ano);

        return fatura;
    }
}
