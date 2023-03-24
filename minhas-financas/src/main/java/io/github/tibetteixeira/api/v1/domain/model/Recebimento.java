package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.RecebimentoDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recebimento")
public class Recebimento {

    @Id
    @GeneratedValue(generator = "recebimento_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "recebimento_id_seq", sequenceName = "recebimento_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(name = "data_recebimento")
    private Date dataRecebimento;

    @Column
    @NonNull
    private BigDecimal valor;

    @Column
    private String descricao;

    @JoinColumn(name = "id_categoria")
    @ManyToOne
    private CategoriaRecebimento categoria;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public RecebimentoDTO toDTO() {
        RecebimentoDTO recebimentoDTO = new RecebimentoDTO();

        recebimentoDTO.setId(id);
        recebimentoDTO.setDataRecebimento(dataRecebimento);
        recebimentoDTO.setValor(valor);
        recebimentoDTO.setDescricao(descricao);
        recebimentoDTO.setCategoria(categoria.toDTO());

        return recebimentoDTO;
    }
}
