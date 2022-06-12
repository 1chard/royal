package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author suporte
 */
public class TransferenciaUsuarioBase {
	public Date data;
	public String descricao;
	public int idCategoria;
	public int idTransferenciaUsuario;
	public Integer indice;
	public int parcelas;
	public BigDecimal valor;

	public TransferenciaUsuarioBase(BigDecimal valor, Date data, String descricao, int idCategoria, int parcelas, Integer indice, int idTransferenciaUsuario) {
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.indice = indice;
		this.parcelas = parcelas;
		this.idTransferenciaUsuario = idTransferenciaUsuario;
	}
}
