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
