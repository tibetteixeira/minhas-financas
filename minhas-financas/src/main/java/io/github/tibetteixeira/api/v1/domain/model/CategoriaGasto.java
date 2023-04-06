package io.github.tibetteixeira.api.v1.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.CategoriaGastoDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "categoria_gasto")
public class CategoriaGasto {

    @Id
    @GeneratedValue(generator = "categoria_gasto_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "categoria_gasto_id_seq", sequenceName = "categoria_gasto_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 100)
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Gasto> gastos = new ArrayList<>();

    public CategoriaGastoDTO toDTO() {
        return CategoriaGastoDTO.builder()
                .id(id)
                .descricao(descricao)
                .build();
    }
}
