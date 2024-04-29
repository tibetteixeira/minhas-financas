CREATE TABLE token (
    chave VARCHAR(255) NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    expirado BOOLEAN NOT NULL DEFAULT false,
    revogado BOOLEAN NOT NULL DEFAULT false,
    id_usuario INTEGER NOT NULL,
    criado_em TIMESTAMP default now(),
    CONSTRAINT pk_token PRIMARY KEY (chave),
    CONSTRAINT fk_token_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    CONSTRAINT check_tipo CHECK (tipo IN ('BEARER', 'BASIC'))
);
