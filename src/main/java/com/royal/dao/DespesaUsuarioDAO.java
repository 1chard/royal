package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
import com.royal.model.ReceitaUsuario;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suporte
 */
public class DespesaUsuarioDAO {
    public static boolean gravar(DespesaUsuario despesaUsuario) throws SQLException{
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
		despesaUsuario.nomeFrequencia,
		despesaUsuario.idUsuario,
		despesaUsuario.idCategoria
		);
	
	return true;
    }
	
	public static BigDecimal despesaGeral(int quem) throws SQLException{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblDespesaUsuario WHERE idusuario = ?", 
				quem
		);
		
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
	}
    
    public static BigDecimal despesaMensal(int quem, int ano, int mes) throws SQLException{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", 
				quem,
				ano,
				mes
		);
		
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
	}
    
    public static List<DespesaUsuario> listarPorMes(int quem, int ano, int mes) throws SQLException{
	var query = Sistema.BANCO.query("SELECT * FROM tblDespesaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", quem, ano, mes);
	
	var list = new ArrayList<DespesaUsuario>();
	
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
	
	return list;
    }
}
