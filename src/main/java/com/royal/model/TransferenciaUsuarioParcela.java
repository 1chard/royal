package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author richard
 */
public class TransferenciaUsuarioParcela {
	public java.sql.Date data;
	public int idTransferenciaUsuario;
	public Integer idTransferenciaUsuarioParcela;
	public int idUsuario;
	public BigDecimal valor;

	public TransferenciaUsuarioParcela(Integer idTransferenciaUsuarioParcela, BigDecimal valor, Date data, int idTransferenciaUsuario, int idUsuario) {
		this.idTransferenciaUsuarioParcela = idTransferenciaUsuarioParcela;
		this.valor = valor;
		this.data = data;
		this.idTransferenciaUsuario = idTransferenciaUsuario;
		this.idUsuario = idUsuario;
	}


}
