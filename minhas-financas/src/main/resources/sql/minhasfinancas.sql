-- DROP DATABASE IF EXISTS minhasfinancas;

-- CREATE DATABASE minhasfinancas;

CREATE SEQUENCE usuario_id_seq START 1 INCREMENT 1;
CREATE TABLE usuario (
    id INTEGER NOT NULL DEFAULT nextval('usuario_id_seq'),
    nome VARCHAR(30) NOT NULL,
    sobrenome VARCHAR(30) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    data_criacao TIMESTAMP,
    data_atualizacao TIMESTAMP,
    data_exclusao TIMESTAMP,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_usuario_id ON usuario USING btree (id);


CREATE SEQUENCE cartao_id_seq START 1 INCREMENT 1;
CREATE TABLE cartao (
    id INTEGER NOT NULL DEFAULT nextval('cartao_id_seq'),
    id_usuario INTEGER NOT NULL,
    nome VARCHAR(20) NOT NULL,
    num_final_cartao VARCHAR(4) NOT NULL,
    dia_vencimento INTEGER,
    CONSTRAINT pk_cartao PRIMARY KEY (id),
    CONSTRAINT fk_cartao_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);
CREATE UNIQUE INDEX idx_cartao_id ON cartao USING btree (id);


CREATE SEQUENCE fatura_id_seq START 1 INCREMENT 1;
CREATE TABLE fatura (
    id INTEGER NOT NULL DEFAULT nextval('fatura_id_seq'),
    id_cartao INTEGER NOT NULL,
    data_vencimento DATE,
    mes VARCHAR(10) NOT NULL,
    ano INTEGER NOT NULL,
    status_pagamento_fatura VARCHAR(20),
    valor_pago NUMERIC(11,2),
    CONSTRAINT pk_fatura PRIMARY KEY (id),
    CONSTRAINT fk_fatura_cartao_id FOREIGN KEY (id_cartao) REFERENCES cartao(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX idx_fatura_id ON fatura USING btree (id);

CREATE SEQUENCE categoria_gasto_id_seq START 1 INCREMENT 1;
CREATE TABLE categoria_gasto (
    id INTEGER NOT NULL DEFAULT nextval('categoria_gasto_id_seq'),
    descricao VARCHAR(50),
    CONSTRAINT pk_categoria_gasto PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_categoria_gasto_id ON categoria_gasto USING btree (id);

CREATE SEQUENCE gasto_id_seq START 1 INCREMENT 1;
CREATE TABLE gasto (
    id INTEGER NOT NULL DEFAULT nextval('gasto_id_seq'),
    id_fatura INTEGER,
    id_categoria INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    data_gasto DATE NOT NULL,
    valor NUMERIC(11,2) NOT NULL,
    descricao VARCHAR(255),
    CONSTRAINT pk_gasto PRIMARY KEY (id),
    CONSTRAINT fk_gasto_fatura_id FOREIGN KEY (id_fatura) REFERENCES fatura(id) ON DELETE CASCADE,
    CONSTRAINT fk_gasto_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT fk_gasto_categoria_gasto_id FOREIGN KEY (id_categoria) REFERENCES categoria_gasto(id)
);
CREATE UNIQUE INDEX idx_gasto_id ON gasto USING btree (id);

CREATE SEQUENCE pagamento_id_seq START 1 INCREMENT 1;
CREATE TABLE pagamento (
    id INTEGER NOT NULL DEFAULT nextval('pagamento_id_seq'),
    id_fatura INTEGER,
    id_usuario INTEGER NOT NULL,
    data_pagamento DATE,
    valor NUMERIC(11,2),
    descricao VARCHAR(255),
    tipo_pagamento VARCHAR(20),
    CONSTRAINT pk_pagamento PRIMARY KEY (id),
    CONSTRAINT fk_pagamento_fatura_id FOREIGN KEY (id_fatura) REFERENCES fatura(id),
    CONSTRAINT fk_pagamento_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);
CREATE UNIQUE INDEX idx_pagamento_id ON pagamento USING btree (id);

CREATE SEQUENCE caixa_economia_id_seq START 1 INCREMENT 1;
CREATE TABLE caixa_economia (
    id INTEGER NOT NULL DEFAULT nextval('caixa_economia_id_seq'),
    id_usuario INTEGER NOT NULL,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    valor_objetivo NUMERIC(11,2),
    valor_economizado NUMERIC(11,2),
    data_criacao TIMESTAMP,
    prazo INTEGER,
    CONSTRAINT pk_caixa_economia PRIMARY KEY (id),
    CONSTRAINT fk_caixa_economia_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);
CREATE UNIQUE INDEX idx_caixa_economia_id ON caixa_economia USING btree (id);

CREATE SEQUENCE item_caixa_economia_id_seq START 1 INCREMENT 1;
CREATE TABLE item_caixa_economia (
    id INTEGER NOT NULL DEFAULT nextval('item_caixa_economia_id_seq'),
    id_caixa_economia INTEGER NOT NULL,
    valor NUMERIC(11,2),
    data_economia TIMESTAMP,
    CONSTRAINT pk_item_caixa_economia PRIMARY KEY (id),
    CONSTRAINT fk_item_caixa_economia_caixa_economia_id FOREIGN KEY (id_caixa_economia) REFERENCES caixa_economia(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX idx_item_caixa_economia_id ON item_caixa_economia USING btree (id);

CREATE SEQUENCE recebimento_id_seq START 1 INCREMENT 1;
CREATE TABLE recebimento (
    id INTEGER NOT NULL DEFAULT nextval('recebimento_id_seq'),
    id_usuario INTEGER NOT NULL,
    tipo_recebimento varchar(20) NOT NULL,
    data_recebimento DATE NOT NULL,
    valor NUMERIC(11,2) NOT NULL,
    descricao VARCHAR(255),
    CONSTRAINT pk_recebimento PRIMARY KEY (id),
    CONSTRAINT fk_recebimento_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);
CREATE UNIQUE INDEX idx_recebimento_id ON recebimento USING btree (id);

CREATE OR REPLACE FUNCTION f_atualizar_valor_economizado_total_caixa()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE caixa_economia ce
    SET valor_economizado = (SELECT SUM(valor) FROM item_caixa_economia ice WHERE ice.id_caixa_economia = COALESCE(NEW.id_caixa_economia, OLD.id_caixa_economia))
    WHERE ce.id = COALESCE(NEW.id_caixa_economia, OLD.id_caixa_economia);

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mes_mapa(mes_texto text)
RETURNS text AS
$$
BEGIN
    CASE mes_texto
        WHEN 'JANEIRO' THEN RETURN 'Jan';
        WHEN 'FEVEREIRO' THEN RETURN 'Feb';
        WHEN 'MARÇO' THEN RETURN 'Mar';
        WHEN 'ABRIL' THEN RETURN 'Apr';
        WHEN 'MAIO' THEN RETURN 'May';
        WHEN 'JUNHO' THEN RETURN 'Jun';
        WHEN 'JULHO' THEN RETURN 'Jul';
        WHEN 'AGOSTO' THEN RETURN 'Aug';
        WHEN 'SETEMBRO' THEN RETURN 'Sep';
        WHEN 'OUTUBRO' THEN RETURN 'Oct';
        WHEN 'NOVEMBRO' THEN RETURN 'Nov';
        WHEN 'DEZEMBRO' THEN RETURN 'Dec';
        ELSE RAISE EXCEPTION 'Mês inválido: %', mes_texto;
    END CASE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION f_setar_data_vencimento_fatura()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE fatura f
    SET data_vencimento = (select to_date(f.ano::text || ' ' || mes_mapa(f.mes) || ' ' || c.dia_vencimento::text , 'yyyy-Mon-dd')
                          from fatura f
                          inner join cartao c on c.id = f.id_cartao
                          where id_cartao = NEW.id_cartao and mes = NEW.mes and ano = NEW.ano)
    WHERE id_cartao = NEW.id_cartao and mes = NEW.mes and ano = NEW.ano;

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER t_atualizar_valor_economizado_total_caixa
AFTER INSERT OR UPDATE OR DELETE ON item_caixa_economia
FOR EACH ROW
EXECUTE PROCEDURE f_atualizar_valor_economizado_total_caixa();

CREATE TRIGGER t_setar_data_vencimento_fatura
AFTER INSERT ON fatura
FOR EACH ROW
EXECUTE PROCEDURE f_setar_data_vencimento_fatura();

-- # INSERTS

-- ## CATEGORIA GASTO
INSERT INTO categoria_gasto (id, descricao) VALUES (1, 'Outro');
INSERT INTO categoria_gasto (id, descricao) VALUES (2, 'Desconhecido');
INSERT INTO categoria_gasto (id, descricao) VALUES (3, 'Mercado');
INSERT INTO categoria_gasto (id, descricao) VALUES (4, 'Farmácia');
INSERT INTO categoria_gasto (id, descricao) VALUES (5, 'Eletrônico');
INSERT INTO categoria_gasto (id, descricao) VALUES (6, 'Padaria');
INSERT INTO categoria_gasto (id, descricao) VALUES (7, 'Alimentação');
INSERT INTO categoria_gasto (id, descricao) VALUES (8, 'Transporte');
INSERT INTO categoria_gasto (id, descricao) VALUES (9, 'Utensílio');
INSERT INTO categoria_gasto (id, descricao) VALUES (10, 'Presente');
INSERT INTO categoria_gasto (id, descricao) VALUES (11, 'Roupa');
INSERT INTO categoria_gasto (id, descricao) VALUES (12, 'Acessório');
INSERT INTO categoria_gasto (id, descricao) VALUES (13, 'Calçado');
INSERT INTO categoria_gasto (id, descricao) VALUES (14, 'Perfumaria');
INSERT INTO categoria_gasto (id, descricao) VALUES (15, 'Livro');
INSERT INTO categoria_gasto (id, descricao) VALUES (16, 'Jogo');
INSERT INTO categoria_gasto (id, descricao) VALUES (17, 'Curso');
INSERT INTO categoria_gasto (id, descricao) VALUES (18, 'Educação');
INSERT INTO categoria_gasto (id, descricao) VALUES (19, 'Saúde');
INSERT INTO categoria_gasto (id, descricao) VALUES (20, 'Academia');

SELECT setval('categoria_gasto_id_seq', 20, true); 