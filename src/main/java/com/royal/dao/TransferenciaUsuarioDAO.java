package com.royal.dao;

import com.royal.Sistema;
import com.royal.model.Frequencia;
import com.royal.model.TransferenciaUsuario;
import com.royal.model.TransferenciaUsuarioBase;
import com.royal.model.TransferenciaUsuarioParcela;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suporte
 */
public class TransferenciaUsuarioDAO {
	public static boolean atualizarFixas(int quem) {
		try {
			Sistema.BANCO.run("call atualizar_parcelas_fixas(?);",
					quem
			);

			return true;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean desfixar(int id) {
		try {
			return Sistema.BANCO.update("call desfixar(?);",
					id
			) > 0;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean desfavoritar(int id) {
		try {
			return Sistema.BANCO.update("update tblTransferenciaUsuario SET favorito = false WHERE idTransferenciaUsuario = ?;",
					id
			) > 0;

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

	public static List<TransferenciaUsuario> fixos(int quem) {
		try {
			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND fixa = true;", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> favoritos(int quem) {
		try {
			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND favorito = true;", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

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

	public static List<TransferenciaUsuarioBase> listar(int quem) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_geral(?);", quem));

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
					query.getInt("idTransferenciaUsuario")
			));
		}

		return list;
	}

	public static List<TransferenciaUsuarioBase> listarAnual(int quem, int ano) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_anual(?, ?);", quem, ano));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuarioBase> listarDespesas(int quem) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_despesa_geral(?);", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuarioBase> listarDespesasAnual(int quem, int ano) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_despesa_anual(?, ?);", quem, ano));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuarioBase> listarDespesasMensal(int quem, int ano, int mes) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_despesa_mensal(?, ?, ?);", quem, ano, mes));

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

	public static List<TransferenciaUsuarioBase> listarReceitas(int quem) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_receita_geral(?);", quem));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuarioBase> listarReceitasAnual(int quem, int ano) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_receita_anual(?, ?);", quem, ano));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuarioBase> listarReceitasMensal(int quem, int ano, int mes) {
		try {
			return queryTratadorBase(Sistema.BANCO.query("call extrato_receita_mensal(?, ?, ?);", quem, ano, mes));

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

	public static BigDecimal saldoLiquidoAnual(int quem, int ano) {
		try {
			var query = Sistema.BANCO.query("call saldo_liquido_anual(?, ?);",
					quem,
					ano
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal saldoLiquidoGeral(int quem) {
		try {
			var query = Sistema.BANCO.query("call saldo_liquido_geral(?);",
					quem
			);

			query.next();

			return query.getBigDecimal("valor");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static BigDecimal saldoLiquidoMensal(int quem, int ano, int mes) {
		try {
			var query = Sistema.BANCO.query("call saldo_liquido_mensal(?, ?, ?);",
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

	public static TransferenciaUsuario buscarId(int quem, int id) {
		try {

			var lista = queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND idTransferenciaUsuario = ?;", quem, id));

			return lista.isEmpty() ? null : lista.get(id);

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<TransferenciaUsuario> buscarIds(int quem, String... ids) {
		try {
			return queryTratador(Sistema.BANCO.query("SELECT * FROM tblTransferenciaUsuario WHERE idusuario = ? AND idTransferenciaUsuario IN(" + String.join(",", ids) + ");", quem));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
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

	private TransferenciaUsuarioDAO() {
	}
}
