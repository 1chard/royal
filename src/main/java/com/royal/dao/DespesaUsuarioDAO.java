package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
import com.royal.model.ReceitaUsuario;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
public class DespesaUsuarioDAO {
    public static boolean gravar(DespesaUsuario despesaUsuario){
	try {
	    Sistema.BANCO.run("INSERT INTO tblDespesaUsuario (valor, data, pendente, anexo, descricao, observacao, favorito, iniciorepeticao, totalparcelas, parcelaspagas, parcelasfixas, nomeFrequencia, idUsuario, idCategoria)" +
		    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
		    despesaUsuario.valor,
		    despesaUsuario.data,
		    despesaUsuario.pendente,
		    despesaUsuario.anexo,
		    despesaUsuario.descricao,
		    despesaUsuario.observacao,
		    despesaUsuario.favorito,
		    despesaUsuario.inicioRepeticao,
		    despesaUsuario.totalParcelas,
		    despesaUsuario.parcelasPagas,
		    despesaUsuario.parcelasFixas,
		    despesaUsuario.nomeFrequencia != null ? despesaUsuario.nomeFrequencia.toString() : null,
		    despesaUsuario.idUsuario,
		    despesaUsuario.idCategoria
	    );
	    
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
	
	public static BigDecimal despesaGeral(int quem){
	    try{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblDespesaUsuario WHERE idusuario = ?", 
				quem
		);
		
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
		} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    public static BigDecimal despesaMensal(int quem, int ano, int mes){
	try{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", 
				quem,
				ano,
				mes
		);
		
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
		} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    public static BigDecimal despesaAnual(int quem, int ano){
	try{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ?;",
				quem,
				ano
		);
		
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
		} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    public static List<DespesaUsuario> listarPorMes(int quem, int ano, int mes){
	return listarPorMes(quem, ano, mes, new String[]{});
    }
    
    public static List<DespesaUsuario> listarPorMes(int quem, int ano, int mes, String[] idcategorias){
	try{
	var list = new ArrayList<DespesaUsuario>();
	
	final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";
	    
	
	queryTratador(
		Sistema.BANCO.query("SELECT * FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?" + extra, quem, ano, mes), list);
	
	return list;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static List<DespesaUsuario> listarPorAno(int quem, int ano){
	return listarPorAno(quem, ano, new String[]{});
    }
    
    public static List<DespesaUsuario> listarPorAno(int quem, int ano, String[] idcategorias){
	try{
	var list = new ArrayList<DespesaUsuario>();
	
	final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";
	    
	
	queryTratador(
		Sistema.BANCO.query("SELECT * FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ?" + extra, quem, ano), list);
	
	return list;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static List<DespesaUsuario> listar(int quem){
	return listar(quem, new String[]{});
    }
    
    public static List<DespesaUsuario> listar(int quem, String[] idcategorias){
	try{
	var list = new ArrayList<DespesaUsuario>();
	
	final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";
	    
	
	queryTratador(
		Sistema.BANCO.query("SELECT * FROM tblDespesaUsuario WHERE idusuario = ?" + extra, quem), list);
	
	return list;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static List<DespesaUsuario> favoritos(int quem){
	try{
	var list = new ArrayList<DespesaUsuario>();
	queryTratador(Sistema.BANCO.query("SELECT * FROM tblDespesaUsuario WHERE idusuario = ? and favorito = true;", quem), list);
	
	return list;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    private static void queryTratador(ResultSet query, List<DespesaUsuario> list) throws SQLException{
	while(query.next()){
	    String nomeFrequencia = query.getString("nomeFrequencia");
	    
	    list.add(
		    new DespesaUsuario(
			    query.getBigDecimal("valor"),
			    query.getDate("data"),
			    query.getDate("pendente"),
			    query.getString("descricao"),
			    query.getBoolean("favorito"),
			    query.getInt("idUsuario"), 
			    query.getInt("idCategoria"),
			    query.getInt("idDespesaUsuario"),
			    query.getString("anexo"), 
			    query.getString("observacao"),
			    query.getDate("inicioRepeticao"),
			    query.getObject("totalParcelas", Integer.class),
			    query.getObject("parcelasPagas", Integer.class),
			    query.getObject("parcelasFixas", Boolean.class),
			    nomeFrequencia != null ? Frequencia.valueOf(nomeFrequencia) : null
		    )
	    );
	}
    }
}
