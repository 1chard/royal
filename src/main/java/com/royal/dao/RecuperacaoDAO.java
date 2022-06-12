package com.royal.dao;

import com.royal.Sistema;
import com.royal.model.Recuperacao;

import java.sql.SQLException;


public class RecuperacaoDAO {
	public static Integer pedir(String email) {
		try {
			var query = Sistema.BANCO.query("select resetar_senha(?) as codigo;", Sistema.ENCRIPTA.encrypt(email));
			query.next();
			return query.getObject("codigo", Integer.class);

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Recuperacao buscar(int idRecuperacao) {
		try {

			var set = Sistema.BANCO.query("SELECT * from tblRecuperacao where idRecuperacao = ?;", idRecuperacao);

			return set.next() ?
					new Recuperacao(
							set.getInt("codigo"),
							set.getInt("idRecuperacao"),
							set.getTimestamp("data"),
							set.getInt("idUsuario")

					) :
					null;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Integer ativa(String email) {
		try {
			var set = Sistema.BANCO.query("select tblRecuperacao.codigo from tblRecuperacao "
					+ "inner join tblUsuario on tblUsuario.idusuario = tblRecuperacao.idusuario "
					+ "where tblUsuario.email = ? and tblRecuperacao.data > date_sub(now(), interval 15 minute)"
					+ "order by tblRecuperacao.idrecuperacao desc limit 1", Sistema.ENCRIPTA.encrypt(email));

			return set.next() ? set.getInt("codigo") : null;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private RecuperacaoDAO() {
	}
}
