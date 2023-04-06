package io.github.tibetteixeira.api.v1.domain.model;

import io.github.tibetteixeira.api.v1.domain.model.dto.RecebimentoDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.TipoRecebimento;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column(name = "tipo_recebimento")
    @Enumerated(EnumType.STRING)
    private TipoRecebimento tipoRecebimento = TipoRecebimento.PIX;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public RecebimentoDTO toDTO() {
        return RecebimentoDTO.builder()
                .id(id)
                .dataRecebimento(dataRecebimento)
                .valor(valor)
                .descricao(descricao)
                .tipoRecebimento(tipoRecebimento)
                .usuario(usuario.toDTO())
                .build();
    }
}
