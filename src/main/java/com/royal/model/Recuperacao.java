package com.royal.model;

import java.sql.Timestamp;

/**
 *
 * @author suporte
 */
public class Recuperacao {
    public int codigo;
    public java.sql.Timestamp data;
	public Integer idRecuperacao;
	public int idUsuario;

    public Recuperacao( int codigo, int idUsuario, Timestamp data, Integer idRecuperacao) {
	this.idRecuperacao = idRecuperacao;
	this.codigo = codigo;
	this.idUsuario = idUsuario;
	this.data = data;
    }
    
    public Recuperacao(int codigo, int idUsuario) {
	this.codigo = codigo;
	this.idUsuario = idUsuario;
    }
    
    
}
