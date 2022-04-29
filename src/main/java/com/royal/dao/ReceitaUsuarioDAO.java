package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suporte
 */
public class ReceitaUsuarioDAO {
    public static boolean gravar(ReceitaUsuario receitaUsuario) throws SQLException{
	Sistema.BANCO.run("INSERT INTO tblReceitaUsuario (valor, data, pendente, anexo, descricao, observacao, favorito, iniciorepeticao, totalparcelas, parcelaspagas, parcelasfixas, nomeFrequencia, idUsuario, idCategoria)" +
		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", 
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
		receitaUsuario.nomeFrequencia,
		receitaUsuario.idUsuario,
		receitaUsuario.idCategoria
		);
	
	return true;
    }
    
	public static BigDecimal receitaGeral(int quem) throws SQLException{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ?;", 
				quem
		);
    
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
	}
	
    public static BigDecimal receitaMensal(int quem, int ano, int mes) throws SQLException{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", 
				quem,
				ano,
				mes
		);
    
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
	}
    
     public static List<ReceitaUsuario> listarPorMes(int quem, int ano, int mes) throws SQLException{
	var query = Sistema.BANCO.query("SELECT * FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", quem, ano, mes);
	
	var list = new ArrayList<ReceitaUsuario>();
	
	while(query.next()){
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
			    query.getInt("totalParcelas"),
			    query.getInt("parcelasPagas"),
			    query.getBoolean("parcelasFixas"),
			    Frequencia.valueOf(query.getString("nomeFrequencia")))
	    );
	}
	
	return list;
    }
}
