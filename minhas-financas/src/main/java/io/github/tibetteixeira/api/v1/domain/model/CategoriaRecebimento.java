package io.github.tibetteixeira.api.v1.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.CategoriaRecebimentoDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria_recebimento")
public class CategoriaRecebimento {

    @Id
    @GeneratedValue(generator = "categoria_recebimento_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "categoria_recebimento_id_seq", sequenceName = "categoria_recebimento_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 100)
    @NonNull
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Recebimento> recebimentos = new ArrayList<>();

    public CategoriaRecebimentoDTO toDTO() {
        CategoriaRecebimentoDTO categoriaRecebimentoDTO = new CategoriaRecebimentoDTO();

        categoriaRecebimentoDTO.setId(id);
        categoriaRecebimentoDTO.setDescricao(descricao);

        return categoriaRecebimentoDTO;
    }
}
