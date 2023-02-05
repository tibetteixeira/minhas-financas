package io.github.tibetteixeira.api.v1.domain.model.dto;

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
}
