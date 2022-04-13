package com.royal.model;

/**
 *
 * @author suporte
 */
public class Categoria {
    public Integer idCategoria;
    public String nome;
    public String cor;
    public String icone;
    public TipoTransferencia tipoTransferencia;

    public Categoria(Integer idCategoria, String nome, String cor, String icone, TipoTransferencia tipoTransferencia) {
	this.idCategoria = idCategoria;
	this.nome = nome;
	this.cor = cor;
	this.icone = icone;
	this.tipoTransferencia = tipoTransferencia;
    }
}
