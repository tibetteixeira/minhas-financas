package io.github.tibetteixeira.api.v1.domain.model.dto;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaturaDTO {

    private Integer id;
    private LocalDate dataVencimento;
    private StatusPagamentoFatura status;
    private BigDecimal valorPago;
    private Integer cartaoId;
    private Integer usuarioId;
    private List<GastoDTO> gastos;
    private BigDecimal totalGastos;

    public FaturaDTO(Integer id, BigDecimal valorPago, BigDecimal totalGastos) {
        this.id = id;
        this.valorPago = valorPago;
        this.totalGastos = totalGastos;
    }

    public Fatura toModel() {
        return Fatura.builder()
                .id(id)
                .dataVencimento(dataVencimento)
                .status(status)
                .valorPago(valorPago)
                .usuario(new Usuario(usuarioId))
                .cartao(new Cartao(cartaoId))
                .build();
    }
}
