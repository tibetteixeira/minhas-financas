package io.github.tibetteixeira.api.v1.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.CartaoDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cartao")
public class Cartao implements Serializable {

    @Id
    @GeneratedValue(generator = "cartao_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "cartao_id_seq", sequenceName = "cartao_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column
    private String nome;

    @Column(name = "num_final_cartao")
    private String ultimosQuatroDigitosCartao;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "cartao")
    private List<Fatura> faturas = new ArrayList<>();

    public Cartao(Integer id) {
        this.id = id;
    }

    public CartaoDTO toDTO() {
        return CartaoDTO.builder()
                .id(id)
                .nome(nome)
                .ultimosQuatroDigitosCartao(ultimosQuatroDigitosCartao)
                .diaVencimento(diaVencimento)
                .usuarioId(usuario.getId())
                .build();

    }
}
