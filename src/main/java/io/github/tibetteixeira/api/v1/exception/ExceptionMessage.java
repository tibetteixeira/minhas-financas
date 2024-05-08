package io.github.tibetteixeira.api.v1.exception;

public class ExceptionMessage {

    private ExceptionMessage(){
        throw new IllegalStateException("Utility class");
    }

    public static final String EMAIL_OU_SENHA_INCORRETO = "Email ou senha incorreto";
    public static final String ID_ROTA_DIFERENTE_ID_OBJETO = "O id da rota é diferente do id do objeto";
    public static final String CAIXINHA_ECONOMIA_NAO_ENCONTRADA = "Caixinha de economia não encontrada";
    public static final String ITEM_CAIXINHA_ECONOMIA_NAO_ENCONTRADO = "Item da caixinha de economia não encontrado";
    public static final String CARTAO_NAO_ENCONTRADO = "Cartão não encontrado";
    public static final String CATEGORIA_GASTO_NAO_ENCONTRADA = "Categoria não encontrada";
    public static final String FATURA_NAO_ENCONTRADA = "Fatura não encontrada";
    public static final String GASTO_NAO_ENCONTRADO = "Gasto não encontrado";
    public static final String PAGAMENTO_NAO_ENCONTRADO = "Pagamento não encontrado";
    public static final String RECEBIMENTO_NAO_ENCONTRADO = "Recebimento não encontrado";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final String USUARIO_NAO_ENCONTRADO_EMAIL_OU_SENHA_INVALIDOS = "Usuário não encontrado. Email ou senha invalidos";
    public static final String USUARIO_NAO_POSSUI_CARTAO_CADASTRADO = "Este usuário não possui cartão cadastrado";
    public static final String NAO_EXISTE_CARTAO_COM_ESSE_NOME = "Não existe cartão com esse nome";
    public static final String NAO_E_POSSIVEL_REMOVER_UMA_CATEGORIA_UTILIZADA = "Não é possível remover uma categoria utilizada";
    public static final String NAO_EXISTE_FATURA_PARA_ESSE_CARTAO = "Não existe fatura para esse cartão";
    public static final String NAO_HA_GASTOS_PARA_ESSA_CATEGORIA = "Não há gastos para essa categoria";
    public static final String NAO_HA_GASTOS_PARA_ESSA_FATURA = "Não há gastos para essa fatura";
    public static final String USUARIO_LOGADO_DIFERENTE_DO_USUARIO_DA_OPERACAOO = "Usuário logado diferente do usuário da operação";
    public static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
    public static final String ID_DO_USUARIO_NAO_PODE_SER_VAZIO = "Id do usuário não pode ser vazio";
    public static final String USUARIO_NAO_PODE_SER_VAZIO = "Usuário não pode ser vazio";
    public static final String NOME_NAO_PODE_SER_VAZIO = "Nome não pode ser vazio";
    public static final String SOBRENOME_NAO_PODE_SER_VAZIO = "Sobrenome não pode ser vazio";
    public static final String EMAIL_NAO_PODE_SER_VAZIO = "Email não pode ser vazio";
    public static final String SENHA_NAO_PODE_SER_VAZIA = "Senha não pode ser vazia";
    public static final String ID_DO_CARTAO_NAO_PODE_SER_VAZIO = "Id do cartão não pode ser vazio";
    public static final String CARTAO_NAO_PODE_SER_VAZIO = "Cartão não pode ser vazio";
    public static final String ULTIMOS_QUATRO_DIGITOS_NAO_PODE_SER_VAZIO = "Os últimos quatro dígitos não pode ser vazio";
    public static final String DIA_VENCIMENTO_NAO_PODE_SER_VAZIO = "O dia do vencimento não pode ser vazio";
    public static final String DIA_VENCIMENTO_NAO_PODE_SER_INVALIDO = "O dia do vencimento não pode ser um valor inválido";
    public static final String FORMATO_JSON_INVALIDO = "Formato do JSON inválido";
    public static final String FORMATO_DATA_INVALIDA = "Formato da data inválida";
    public static final String VALOR_ENUM_INVALIDO = "Valor do ENUM inválido";
    public static final String ID_CATEGORIA_GASTO_NAO_PODE_SER_VAZIO = "Id da categoria de gasto não pode ser vazio";
    public static final String DESCRICAO_NAO_PODE_SER_VAZIA = "Descrição não pode ser vazia";
    public static final String ID_CAIXA_NAO_PODE_SER_VAZIO = "Id da caixinha não pode ser vazio";
    public static final String NOME_CAIXA_NAO_PODE_SER_VAZIO = "Nome da caixinha não pode ser vazio";
    public static final String CAIXA_NAO_PODE_SER_VAZIO = "Caixinha não pode ser vazio";
    public static final String ID_ITEM_CAIXA_NAO_PODE_SER_VAZIO = "Id do item da caixinha não pode ser vazio";
    public static final String ITEM_CAIXA_NAO_PODE_SER_VAZIO = "Item da caixinha não pode ser vazio";
    public static final String ID_NAO_PODE_SER_VAZIO = "O ID não pode ser vazio";
    public static final String GASTO_NAO_PODE_SER_VAZIO = "O gasto não pode ser vazio";
    public static final String DESCRICAO_NAO_PODE_SER_VAZIO = "A descrição não pode ser vazia";
    public static final String VALOR_NAO_PODE_SER_VAZIO = "O valor não pode ser vazio";
    public static final String VALOR_NAO_PODE_SER_ZERO_OU_NEGATIVO = "O valor não pode ser zero ou negativo";
    public static final String FORMA_PAGAMENTO_NAO_PODE_SER_VAZIA = "A forma de pagamento não pode ser vazia";
    public static final String CATEGORIA_NAO_PODE_SER_VAZIA = "A categoria não pode ser vazia";
    public static final String DATA_NAO_PODE_SER_VAZIA = "A data não pode ser vazia";
    public static final String DATA_NAO_PODE_SER_ANTERIOR_A_2010 = "A data não pode ser anterior a 2010";
    public static final String GASTO_POR_CREDITO_DEVE_TER_CARTAO = "O gasto por crédito deve ter algum cartão associado";
    public static final String MES_NAO_PODE_SER_INVALIDO = "O mês não pode ser inválido";
    public static final String PAGAMENTO_NAO_PODE_SER_VAZIO = "O pagamento não pode ser vazio";
    public static final String FATURA_NAO_PODE_SER_VAZIA = "A fatura não pode ser vazia";
    public static final String FORMA_PAGAMENTO_INVALIDA = "Essa forma de pagamento é inválida";
    public static final String RECEBIMENTO_NAO_PODE_SER_VAZIO = "O recebimento não pode ser vazio";
    public static final String TIPO_RECEBIMENTO_NAO_PODE_SER_VAZIO = "O tipo de recebimento não pode ser vazio";

}
