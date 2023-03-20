package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.GastoDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(generator = "fatura_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "fatura_id_seq", sequenceName = "fatura_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_gasto")
    private Date dataGasto;

    @Column
    @NonNull
    private BigDecimal valor;

    @Column
    private String descricao;

    @JoinColumn(name = "id_categoria")
    @ManyToOne
    private CategoriaGasto categoria;

    @JoinColumn(name = "id_fatura")
    @ManyToOne
    private Fatura fatura;

    public GastoDTO toDTO() {
        GastoDTO gastoDTO = new GastoDTO();

        gastoDTO.setId(id);
        gastoDTO.setDataGasto(dataGasto);
        gastoDTO.setValor(valor);
        gastoDTO.setDescricao(descricao);
        gastoDTO.setCategoria(categoria.toDTO());
        gastoDTO.setFatura(fatura.toDTO());

        return gastoDTO;
    }
}
