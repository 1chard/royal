package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author suporte
 */
public class TransferenciaUsuarioBase {
    public BigDecimal valor;
    public Date data;
    public String descricao;
    public int idCategoria;
    public Integer indice;
    public int parcelas;
    public Integer idTransferenciaPai;

    public TransferenciaUsuarioBase(BigDecimal valor, Date data, String descricao, int idCategoria, int parcelas, Integer indice, Integer idTransferenciaPai) {
	this.valor = valor;
	this.data = data;
	this.descricao = descricao;
	this.idCategoria = idCategoria;
	this.indice = indice;
	this.parcelas = parcelas;
	this.idTransferenciaPai = idTransferenciaPai;
    }
}
