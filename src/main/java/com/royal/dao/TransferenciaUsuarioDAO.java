package com.royal.dao;

import com.royal.model.TransferenciaUsuario;
import com.royal.Sistema;
import com.royal.model.Frequencia;
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

	public static boolean gravar(TransferenciaUsuario transferenciaUsuario, Integer parcelas) {
		
		System.out.println(transferenciaUsuario.parcelada);
		System.out.println(parcelas);
		
		try {
			Sistema.BANCO.run("call inserir_transferencia_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
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
					transferenciaUsuario.inicioRepeticao,
					transferenciaUsuario.frequencia != null ? transferenciaUsuario.frequencia.toString() : null,
					parcelas
			);
			

			return true;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal despesaGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call despesaGeral(?);",
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
			var query = Sistema.BANCO.query("call receitaGeral(?);",
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
			var query = Sistema.BANCO.query("call receitaMensal(?, ?, ?);",
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
			var query = Sistema.BANCO.query("call despesaMensal(?, ?, ?);",
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
			var query = Sistema.BANCO.query("call despesaAnual(?, ?);",
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
			var query = Sistema.BANCO.query("call receitaAnual(?, ?);",
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
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0 AND year(data) = ? AND month(data) = ?" + extra, quem, ano, mes), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListarPorMes(int quem, int ano, int mes) {
		return despesaListarPorMes(quem, ano, mes, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListarPorMes(int quem, int ano, int mes, String[] idcategorias) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0 AND year(data) = ? AND month(data) = ?" + extra, quem, ano, mes), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> despesaListarPorAno(int quem, int ano) {
		return despesaListarPorAno(quem, ano, new String[]{});
	}

	public static List<TransferenciaUsuario> despesaListarPorAno(int quem, int ano, String[] idcategorias) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0 AND year(data) = ?" + extra, quem, ano), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListarPorAno(int quem, int ano) {
		return despesaListarPorAno(quem, ano, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListarPorAno(int quem, int ano, String[] idcategorias) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0 AND year(data) = ?" + extra, quem, ano), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> despesaListar(int quem) {
		return despesaListar(quem, new String[]{});
	}

	public static List<TransferenciaUsuario> despesaListar(int quem, String[] idcategorias) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(
					Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor < 0" + extra, quem), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<TransferenciaUsuario> receitaListar(int quem) {
		return despesaListar(quem, new String[]{});
	}

	public static List<TransferenciaUsuario> receitaListar(int quem, String[] idcategorias) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();

			final String extra = idcategorias.length > 0 ? " AND idcategoria IN (" + String.join(",", idcategorias) + ");" : ";";

			queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND valor > 0" + extra, quem), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> favoritos(int quem) {
		try {
			var list = new ArrayList<TransferenciaUsuario>();
			queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? and favorito = true;", quem), list);

			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void queryTratadorParcela(ResultSet query, List<TransferenciaUsuarioParcela> list) throws SQLException {
		while (query.next()) {

			list.add(new TransferenciaUsuarioParcela(
					query.getInt("idTransferenciaUsuarioParcela"),
					query.getBigDecimal("valor"),
					query.getDate("data"),
					query.getInt("idTransferenciaUsuario")
			));
		}
	}
	
	private static void queryTratador(ResultSet query, List<TransferenciaUsuario> list) throws SQLException {
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
					query.getDate("iniciorepeticao"),
					nomeFrequencia != null ? Frequencia.valueOf(nomeFrequencia) : null
			)
			);
		}
	}
}
