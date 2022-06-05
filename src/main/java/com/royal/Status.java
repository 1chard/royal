package com.royal;

/**
 * @author suporte
 */
public enum Status {
    OK(0),
    EMAIL_REPETIDO(1062),
    CAMPO_VAZIO_OU_GRANDE(1),
    CAMPO_INVALIDO(2),
    CAMPO_TIPO_INCORRETO(24),
    JSON_INVALIDO(43),
    SENHA_IDIOTA(3),
    SENHA_INCORRETA(17),
    REQUISICAO_INVALIDA(69);

    public final int codigo;

    Status(int codigo) {
        this.codigo = codigo;
    }
}
