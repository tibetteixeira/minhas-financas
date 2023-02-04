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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(generator = "usuario_id_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column(length = 30)
    @NonNull
    private String nome;

    @Column(length = 30)
    @NonNull
    private String sobrenome;

    @Column(length = 100, unique = true)
    @NonNull
    private String email;

    @Column
    @NonNull
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
    List<Recebimento> recebimentos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    List<CaixaEconomia> caixas = new ArrayList<>();


    public UsuarioDTO toDTO() {

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setId(this.id);
        usuario.setNome(this.nome);
        usuario.setSobrenome(this.sobrenome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);

        return usuario;
    }
}
