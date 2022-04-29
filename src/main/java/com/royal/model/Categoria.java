package com.royal.model;

/**
 *
 * @author suporte
 */
public final class Categoria {
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

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Categoria{");
	sb.append("idCategoria=").append(idCategoria);
	sb.append(", nome=").append(nome);
	sb.append(", cor=").append(cor);
	sb.append(", icone=").append(icone);
	sb.append(", tipoTransferencia=").append(tipoTransferencia);
	sb.append('}');
	return sb.toString();
    }
    
    
}
