package com.royal.dao;

import com.royal.model.DespesaUsuario;
import com.royal.servlet.Sistema;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author suporte
 */
public class DespesaUsuarioDAO {
    public static boolean gravar(DespesaUsuario despesaUsuario) throws SQLException{
	Sistema.BANCO.run("INSERT INTO royal.tblDespesaUsuario (valor, data, pendente, anexo, descricao, observacao, favorito, iniciorepeticao, totalparcelas, parcelaspagas, parcelasfixas, nomeFrequencia, idUsuario, idCategoria)" +
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
    
    public static BigDecimal despesaMensal(int mes, int ano){
	return null;
    }
}
