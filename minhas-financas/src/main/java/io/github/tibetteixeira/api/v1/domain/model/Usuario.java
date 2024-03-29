package io.github.tibetteixeira.api.v1.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tibetteixeira.api.v1.domain.model.dto.UsuarioDTO;
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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(generator = "usuario_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 30)
    private String nome;

    @Column(length = 30)
    private String sobrenome;

    @Column(length = 100, unique = true)
    private String email;

    @Column
    private String senha;

    @Embedded
    private DataAuditoria dataAuditoria = new DataAuditoria();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<Cartao> cartoes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<Pagamento> pagamentos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<Gasto> gastos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<Recebimento> recebimentos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<CaixaEconomia> caixas = new ArrayList<>();

    public Usuario(Integer id) {
        this.id = id;
    }

    public UsuarioDTO toDTO() {
        return UsuarioDTO.builder()
                .id(id)
                .nome(nome)
                .sobrenome(sobrenome)
                .email(email)
                .build();
    }
}
