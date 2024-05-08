ALTER TABLE fatura
    ADD COLUMN id_usuario INTEGER NOT NULL;

ALTER TABLE fatura
    ADD CONSTRAINT fk_fatura_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id);
