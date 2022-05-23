package com.royal.dao;

import com.royal.Pair;
import com.royal.model.TransferenciaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
import com.royal.model.TransferenciaUsuarioBase;
import com.royal.model.TransferenciaUsuarioParcela;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suporte
 */
public class TransferenciaUsuarioDAO {

	public static boolean gravar(TransferenciaUsuario transferenciaUsuario) {
		try {
			Sistema.BANCO.run("call inserir_transferencia_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
					transferenciaUsuario.valor,
					transferenciaUsuario.data,
					transferenciaUsuario.descricao,
					transferenciaUsuario.favorito,
					transferenciaUsuario.parcelada,
					transferenciaUsuario.fixa,
					transferenciaUsuario.idUsuario,
					transferenciaUsuario.idCategoria,
					transferenciaUsuario.anexo,
					transferenciaUsuario.observacao,
					transferenciaUsuario.frequencia != null ? transferenciaUsuario.frequencia.toString() : null,
					transferenciaUsuario.parcelas 
			);
			

			return true;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call despesa_geral(?);",
					quem
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal receitaGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call receita_geral(?);",
					quem
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaMensal(int quem, int ano, int mes) {
		try {
			var query = Sistema.BANCO.query("call despesa_mensal(?, ?, ?);",
					quem,
					ano,
					mes
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static BigDecimal receitaMensal(int quem, int ano, int mes) {
		try {
			var query = Sistema.BANCO.query("call receita_mensal(?, ?, ?);",
					quem,
					ano,
					mes
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaAnual(int quem, int ano) {
		try {
			var query = Sistema.BANCO.query("call despesa_anual(?, ?);",
					quem,
					ano
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static BigDecimal receitaAnual(int quem, int ano) {
		try {
			var query = Sistema.BANCO.query("call receita_anual(?, ?);",
					quem,
					ano
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
		public static BigDecimal despesaLiquidaGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call despesa_liquida_geral(?);",
					quem
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal receitaLiquidaGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call receita_liquida_geral(?);",
					quem
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaLiquidaMensal(int quem, int ano, int mes) {
		try {
			var query = Sistema.BANCO.query("call despesa_liquida_mensal(?, ?, ?);",
					quem,
					ano,
					mes
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static BigDecimal receitaLiquidaMensal(int quem, int ano, int mes) {
		try {
			var query = Sistema.BANCO.query("call receita_liquida_mensal(?, ?, ?);",
					quem,
					ano,
					mes
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaLiquidaAnual(int quem, int ano) {
		try {
			var query = Sistema.BANCO.query("call despesa_liquida_anual(?, ?);",
					quem,
					ano
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static BigDecimal receitaLiquidaAnual(int quem, int ano) {
		try {
			var query = Sistema.BANCO.query("call receita_liquida_anual(?, ?);",
					quem,
					ano
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> despesaListarPorMes(int quem, int ano, int mes) {
		return despesaListarPorMes(quem, ano, mes, new String[]{});
	}

	public static List<TransferenciaUsuario> despesaListarPorMes(int quem, int ano, int mes, String[] idcategorias) {
		try {

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0 AND year(data) = ? AND month(data) = ?" + extra, quem, ano, mes));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListarPorMes(int quem, int ano, int mes) {
		return receitaListarPorMes(quem, ano, mes, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListarPorMes(int quem, int ano, int mes, String[] idcategorias) {
		try {

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0 AND year(data) = ? AND month(data) = ?" + extra, quem, ano, mes));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> despesaListarPorAno(int quem, int ano) {
		return despesaListarPorAno(quem, ano, new String[]{});
	}

	public static List<TransferenciaUsuario> despesaListarPorAno(int quem, int ano, String[] idcategorias) {
		try {
			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0 AND year(data) = ?" + extra, quem, ano));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListarPorAno(int quem, int ano) {
		return receitaListarPorAno(quem, ano, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListarPorAno(int quem, int ano, String[] idcategorias) {
		try {
			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0 AND year(data) = ?" + extra, quem, ano));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> despesaListar(int quem) {
		return despesaListar(quem, new String[]{});
	}

	public static List<TransferenciaUsuario> despesaListar(int quem, String[] idcategorias) {
		try {
			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0" + extra, quem));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListar(int quem) {
		return receitaListar(quem, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListar(int quem, String[] idcategorias) {
		try {
			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0" + extra, quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuarioBase> listar(int quem) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_geral(?);", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuarioBase> listarAnual(int quem, int ano) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_anual(?, ?);", quem, ano));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuarioBase> listarMensal(int quem, int ano, int mes) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_mensal(?, ?, ?);", quem, ano, mes));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<Pair<TransferenciaUsuario, Integer>> favoritos(int quem) {
		try {
			return queryTratadorComParcelas(Sistema.BANCO.query("SELECT *, (select count(*) from tblTransferenciaUsuarioParcela where idTransferenciaUsuario = tblTransferenciaUsuario.idTransferenciaUsuario) as parcelas FROM tblTransferenciaUsuario WHERE idusuario = ? AND favorito = true;", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static List<TransferenciaUsuarioBase> queryTratadorBase(ResultSet query) throws SQLException {
	    var list = new ArrayList<TransferenciaUsuarioBase>();
	    
		while (query.next()) {

			list.add(new TransferenciaUsuarioBase(
					query.getBigDecimal("valor"),
					query.getDate("data"),
					query.getString("descricao"),
					query.getInt("idCategoria"),
					query.getInt("parcelas"),
					query.getObject("indice", Integer.class),
					query.getObject("idTransferenciaPai", Integer.class)
			));
		}
		
		return list;
	}
	
	private static List<TransferenciaUsuarioParcela> queryTratadorParcela(ResultSet query) throws SQLException {
	    var list = new ArrayList<TransferenciaUsuarioParcela>();
	    
		while (query.next()) {

			list.add(new TransferenciaUsuarioParcela(
					query.getInt("idTransferenciaUsuarioParcela"),
					query.getBigDecimal("valor"),
					query.getDate("data"),
					query.getInt("idTransferenciaUsuario"),
					query.getInt("idCategoria")
			));
		}
		
		return list;
	}
	
	private static List<TransferenciaUsuario> queryTratador(ResultSet query) throws SQLException {
	    var list = new ArrayList<TransferenciaUsuario>();
	    
		while (query.next()) {
			String nomeFrequencia = query.getString("frequencia");

			list.add(new TransferenciaUsuario(
					query.getBigDecimal("valor"),
					query.getDate("data"),
					query.getString("descricao"),
					query.getBoolean("favorito"),
					query.getBoolean("parcelada"),
					query.getBoolean("fixa"),
					query.getInt("idUsuario"),
					query.getInt("idCategoria"),
					query.getObject("idTransferenciaUsuario", Integer.class),
					query.getString("anexo"),
					query.getString("observacao"),
					nomeFrequencia != null ? Frequencia.valueOf(nomeFrequencia) : null,
					query.getInt("parcelas")
			)
			);
		}
		
		return list;
	}
	
	private static List<Pair<TransferenciaUsuario, Integer>> queryTratadorComParcelas(ResultSet query) throws SQLException {
	    var list = new ArrayList<Pair<TransferenciaUsuario, Integer>>();
	    
		while (query.next()) {
			String nomeFrequencia = query.getString("frequencia");

			list.add(
			Pair.of(
				new TransferenciaUsuario(
					query.getBigDecimal("valor"),
					query.getDate("data"),
					query.getString("descricao"),
					query.getBoolean("favorito"),
					query.getBoolean("parcelada"),
					query.getBoolean("fixa"),
					query.getInt("idUsuario"),
					query.getInt("idCategoria"),
					query.getObject("idTransferenciaUsuario", Integer.class),
					query.getString("anexo"),
					query.getString("observacao"),
					nomeFrequencia != null ? Frequencia.valueOf(nomeFrequencia) : null,
					query.getInt("parcelas")
				),
				query.getInt("parcelas")
			)
			);
		}
		
		return list;
	}
}
