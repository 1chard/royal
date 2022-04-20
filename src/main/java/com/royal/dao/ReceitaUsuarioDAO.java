package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.Sistema;
import java.math.BigDecimal;
import java.sql.SQLException;

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
    
    public static BigDecimal despesaMensal(int quem, int ano, int mes) throws SQLException{
		var query = Sistema.BANCO.query("SELECT ifnull(sum(valor), 0) FROM tblReceitaUsuario WHERE idusuario = ? AND year(data) = ? AND month(data) = ?;", 
				quem,
				ano,
				mes
		);
    
		query.next();
		
		return query.getBigDecimal("ifnull(sum(valor), 0)");
	}
}
