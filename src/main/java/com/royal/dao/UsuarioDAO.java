package com.royal.dao;

import com.royal.Sistema;
import com.royal.Status;
import com.royal.model.Usuario;

import java.sql.SQLException;


public class UsuarioDAO {
	public static boolean gravar(Usuario user) {
		try {
			Sistema.BANCO.run("INSERT into tblUsuario(nome, email, senha, foto, duasetapas) values(?, ?, ?, ?, ?);",
					Sistema.ENCRIPTA.encrypt(user.nome),
					Sistema.ENCRIPTA.encrypt(user.email),
					Sistema.ENCRIPTA.encrypt(user.senha),
					user.foto,
					user.duasetapas
			);

			return true;
		} catch (SQLException ex) {
			if (ex.getErrorCode() == Status.EMAIL_REPETIDO.codigo) {
				return false;
			} else {
				throw new RuntimeException(ex);
			}
		}
	}

	public static Usuario buscar(String email, String senha) {

		try {
			var set = Sistema.BANCO.query("SELECT * from tblUsuario where email = ? and senha = ?;", Sistema.ENCRIPTA.encrypt(email), Sistema.ENCRIPTA.encrypt(senha));

			return set.next() ?
					new Usuario(
							Sistema.DESENCRIPTA.decrypt(set.getString("nome")),
							Sistema.DESENCRIPTA.decrypt(set.getString("email")),
							Sistema.DESENCRIPTA.decrypt(set.getString("senha")),
							set.getBoolean("duasetapas"),
							set.getString("foto"),
							set.getInt("idusuario")
					) :
					null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	public static Usuario buscar(String email) {

		try {
			var set = Sistema.BANCO.query("SELECT * from tblUsuario where email = ?;", Sistema.ENCRIPTA.encrypt(email));

			return set.next() ?
					new Usuario(
							Sistema.DESENCRIPTA.decrypt(set.getString("nome")),
							Sistema.DESENCRIPTA.decrypt(set.getString("email")),
							Sistema.DESENCRIPTA.decrypt(set.getString("senha")),
							set.getBoolean("duasetapas"),
							set.getString("foto"),
							set.getInt("idusuario")
					) :
					null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	public static Usuario buscar(int id) {

		try {
			var set = Sistema.BANCO.query("SELECT * from tblUsuario where idUsuario = ?;", id);

			return set.next() ?
					new Usuario(
							Sistema.DESENCRIPTA.decrypt(set.getString("nome")),
							Sistema.DESENCRIPTA.decrypt(set.getString("email")),
							Sistema.DESENCRIPTA.decrypt(set.getString("senha")),
							set.getBoolean("duasetapas"),
							set.getString("foto"),
							set.getInt("idUsuario")
					) :
					null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	public static boolean editarSenhaPorEmail(String email, String novaSenha) {
		try {
			return Sistema.BANCO.update("UPDATE tblUsuario SET senha=? where email=?;",
					Sistema.ENCRIPTA.encrypt(novaSenha),
					Sistema.ENCRIPTA.encrypt(email)
			) > 0;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean editarSenhaPorId(int idusuario, String senha) {
		try {
			return Sistema.BANCO.update("UPDATE tblUsuario SET senha=? where idUsuario=?;",
					Sistema.ENCRIPTA.encrypt(senha),
					idusuario
			) > 0;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean editarNomeDuasEtapasFoto(int idusuario, String nome, boolean duasetapas, String foto) {
		try {
			return Sistema.BANCO.update("UPDATE tblUsuario SET nome=?, foto=?, duasetapas=? where idUsuario=?;",
					Sistema.ENCRIPTA.encrypt(nome),
					foto,
					duasetapas,
					idusuario
			) > 0;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private UsuarioDAO() {
	}
}
