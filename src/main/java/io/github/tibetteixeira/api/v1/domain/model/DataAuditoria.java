package io.github.tibetteixeira.api.v1.domain.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class DataAuditoria implements Serializable {

    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "data_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Column(name = "data_exclusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExclusao;
}
