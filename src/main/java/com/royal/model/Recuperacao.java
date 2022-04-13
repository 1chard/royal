package com.royal.model;

import java.sql.Timestamp;

/**
 *
 * @author suporte
 */
public class Recuperacao {
    public Integer idRecuperacao;
    public int codigo, idUsuario;
    public java.sql.Timestamp data;

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
