package io.github.tibetteixeira.api.v1.controller;

public class Rotas {

    private Rotas() {
        throw new IllegalStateException("Utility class");
    }

    public static final String EMPTY = "";
    public static final String ID = "/{id}";

    public static final String NOME = "nome/{nome}";
    public static final String DESCRICAO = "descricao/{descricao}";
    public static final String USUARIO_ID = "usuario/{usuario}";
    public static final String USUARIO_PARAM = "usuario";
    public static final String CARTAO_ID = "cartao/{cartao}";
    public static final String ITEM_CAIXA_ECONOMIA = "/item";
    public static final String DATA = "/data";
    public static final String AUTH = "/api/v1/auth";
    public static final String LOGOUT = "/logout";
    public static final String DOCS = "/docs";
    public static final String USUARIO = "/api/v1/usuario";
    public static final String CARTAO = "/api/v1/cartao";
    public static final String FATURA = "/api/v1/fatura";
    public static final String CATEGORIA_GASTO = "/api/v1/categoria_gasto";
    public static final String GASTO = "/api/v1/gasto";
    public static final String PAGAMENTO = "/api/v1/pagamento";
    public static final String RECEBIMENTO = "/api/v1/recebimento";
    public static final String CAIXA_ECONOMIA = "/api/v1/caixa";
}
