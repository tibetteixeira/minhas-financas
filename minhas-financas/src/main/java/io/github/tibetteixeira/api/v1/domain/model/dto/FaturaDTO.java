package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.enums.Mes;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaturaDTO {

    private Integer id;
    private Date dataVencimento;
    private StatusPagamentoFatura status;
    private BigDecimal valorPago;
    private CartaoDTO cartao;
    private Mes mes;
    private Integer ano;
    private List<GastoDTO> gastos;

    public Fatura toModel() {
        return Fatura.builder()
                .id(id)
                .dataVencimento(dataVencimento)
                .status(status)
                .valorPago(valorPago)
                .cartao(cartao.toModel())
                .mes(mes)
                .ano(ano)
                .build();
    }
}
