package io.github.tibetteixeira.api.configuration.security.model;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Entity
@Table(name = "token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {

    @Id
    @Column
    private String chave;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoToken tipo = TipoToken.BEARER;

    @Column
    private Boolean expirado;

    @Column
    private Boolean revogado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    public Usuario usuario;

    public boolean valido() {
        return isFalse(revogado) && isFalse(expirado);
    }

}
