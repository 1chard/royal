package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author richard
 */
public class TransferenciaUsuarioParcela {
	public Integer idTransferenciaUsuarioParcela;
	public BigDecimal valor;
	public java.sql.Date data;
	public int idTransferenciaUsuario;
	public int idCategoria;

	public TransferenciaUsuarioParcela(Integer idTransferenciaUsuarioParcela, BigDecimal valor, Date data, int idTransferenciaUsuario, int idCategoria) {
		this.idTransferenciaUsuarioParcela = idTransferenciaUsuarioParcela;
		this.valor = valor;
		this.data = data;
		this.idTransferenciaUsuario = idTransferenciaUsuario;
		this.idCategoria = idCategoria;
	}
	
	
}
