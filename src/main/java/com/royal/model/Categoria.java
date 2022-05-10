package com.royal.model;

import com.royal.dao.CategoriaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static final List<Categoria> DESPESAS = Collections.synchronizedList(new ArrayList<>());
    public static final List<Categoria> RECEITAS = Collections.synchronizedList(new ArrayList<>());
    
    static{
	    CategoriaDAO.listar().forEach(categoria -> {
		System.out.println(categoria);
		
		if(null == categoria.tipoTransferencia){
		    throw new RuntimeException();
		} else switch (categoria.tipoTransferencia) {
		    case DESPESA -> DESPESAS.add(categoria);
		    case RECEITA -> RECEITAS.add(categoria);
		}
	    });
    }
    
    public static Categoria despesaPorId(int id){
	for(int i = 0; i < DESPESAS.size(); i++){
	    var despesa = DESPESAS.get(i);
	    
	    if(despesa.idCategoria == id){
		return despesa;
	    }
	}
	
	return null;
    }
    
    public static Categoria receitaPorId(int id){
	for(int i = 0; i < RECEITAS.size(); i++){
	    var receita = RECEITAS.get(i);
	    
	    if(receita.idCategoria == id){
		return receita;
	    }
	}
	
	return null;
    }
}
