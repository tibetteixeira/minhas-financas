package io.github.tibetteixeira.api.v1.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class DataAuditoria {

    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column(name = "data_atualizacao")
    private Date dataAtualizacao;

    @Column(name = "data_exclusao")
    private Date dataExclusao;
}
