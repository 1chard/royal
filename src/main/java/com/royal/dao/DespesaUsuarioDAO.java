package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.Sistema;
import java.math.BigDecimal;
import java.sql.SQLException;

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
}
