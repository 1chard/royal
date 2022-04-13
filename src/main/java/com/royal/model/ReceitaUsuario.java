package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author suporte
 */
public class ReceitaUsuario {
    public Integer idReceitaUsuario;
    public BigDecimal valor;
    public java.sql.Date data;
    public Frequencia nomeFrequencia;
    public boolean pendente;
    public String anexo, observacao, descricao;
}
