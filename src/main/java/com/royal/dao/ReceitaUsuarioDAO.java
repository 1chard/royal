package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
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
public class ReceitaUsuarioDAO {

    public static boolean gravar(ReceitaUsuario receitaUsuario) {
	try {
	    Sistema.BANCO.run("INSERT INTO tblReceitaUsuario (valor, data, pendente, anexo, descricao, observacao, favorito, iniciorepeticao, totalparcelas, parcelaspagas, parcelasfixas, nomeFrequencia, idUsuario, idCategoria)"
		    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
		    receitaUsuario.valor,
		    receitaUsuario.data,
		    receitaUsuario.pendente,
		    receitaUsuario.anexo,
		    receitaUsuario.descricao,
		    receitaUsuario.observacao,
		    receitaUsuario.favorito,
		    receitaUsuario.inicioRepeticao,
		    receitaUsuario.totalParcelas,
		    receitaUsuario.parcelasPagas,
		    receitaUsuario.parcelasFixas,
		    receitaUsuario.nomeFrequencia != null ? receitaUsuario.nomeFrequencia.toString() : null,
		    receitaUsuario.idUsuario,
		    receitaUsuario.idCategoria
	    );
	    
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public static BigDecimal receitaGeral(int quem) {
	try{
	var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ?;",
		quem
	);

	query.next();

	return query.getBigDecimal("ifnull(sum(valor), 0)");
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public static BigDecimal receitaMensal(int quem, int ano, int mes) {
	try{
	var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;",
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
    
    public static BigDecimal receitaAnual(int quem, int ano) {
	try{
	var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;",
		quem,
		ano
	);

	query.next();

	return query.getBigDecimal("ifnull(sum(valor), 0)");
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public static List<ReceitaUsuario> listarPorMes(int quem, int ano, int mes) {
	try{

	var list = new ArrayList<ReceitaUsuario>();
	queryTratador(Sistema.BANCO.query("SELECT * FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", quem, ano, mes), list);

	return list;
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    public static List<ReceitaUsuario> listarPorAno(int quem, int ano) {
	try{
	var list = new ArrayList<ReceitaUsuario>();
	queryTratador(Sistema.BANCO.query("SELECT * FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ?;", quem, ano), list);

	return list;
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    public static List<ReceitaUsuario> listar(int quem) {
	try{
	    var list = new ArrayList<ReceitaUsuario>();
	
	queryTratador(Sistema.BANCO.query("SELECT * FROM tblReceitaUsuario WHERE idusuario = ?;", quem), list);

	return list;
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static List<ReceitaUsuario> favoritos(int quem){
	try{
	var list = new ArrayList<ReceitaUsuario>();
	queryTratador(Sistema.BANCO.query("SELECT * FROM tblReceitaUsuario WHERE idusuario = ? and favorito = true;", quem), list);
	
	
	return list;
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	}
    
    private static void queryTratador(ResultSet query, List<ReceitaUsuario> list) throws SQLException{
	while (query.next()) {
	    String nomeFrequencia = query.getString("nomeFrequencia");

	    list.add(
		    new ReceitaUsuario(
			    query.getBigDecimal("valor"),
			    query.getDate("data"),
			    query.getDate("pendente"),
			    query.getString("descricao"),
			    query.getBoolean("favorito"),
			    query.getInt("idUsuario"),
			    query.getInt("idCategoria"),
			    query.getInt("idReceitaUsuario"),
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
