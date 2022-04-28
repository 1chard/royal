package com.royal.dao;

import com.royal.jdbc.MySQL;
import com.royal.model.Usuario;
import com.royal.Sistema;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
public class UsuarioDAO {
    public static boolean gravar(Usuario user) throws SQLException{
	    Sistema.BANCO.run("INSERT into tblUsuario(nome, email, senha, foto, duasetapas) values(?, ?, ?, ?, ?);", 
		    Sistema.ENCRIPTA.encrypt(user.nome), 
		    Sistema.ENCRIPTA.encrypt(user.email),
		    Sistema.ENCRIPTA.encrypt(user.senha),
		    user.foto,
		    user.duasetapas
	    );
	    return true;
    }
    
    public static Usuario buscar(String email, String senha) throws SQLException{

	    var set = Sistema.BANCO.query("SELECT * from tblUsuario where email = ? and senha = ?;", Sistema.ENCRIPTA.encrypt(email), Sistema.ENCRIPTA.encrypt(senha));
	    
	    return set.next() ?
		    new Usuario(
			    Sistema.DESENCRIPTA.decrypt(set.getString("nome")),
			    Sistema.DESENCRIPTA.decrypt(set.getString("email")),
			    Sistema.DESENCRIPTA.decrypt(set.getString("senha")), 
			    set.getBoolean("duasetapas"),
			    set.getString("foto"),
			    set.getInt("idusuario")
		    ):
		    null;

    }
    
    public static Usuario buscar(String email) throws SQLException{

	    var set = Sistema.BANCO.query("SELECT * from tblUsuario where email = ?;", Sistema.ENCRIPTA.encrypt(email));
	    
	    return set.next() ?
		    new Usuario(
			    Sistema.DESENCRIPTA.decrypt(set.getString("nome")),
			    Sistema.DESENCRIPTA.decrypt(set.getString("email")),
			    Sistema.DESENCRIPTA.decrypt(set.getString("senha")), 
			    set.getBoolean("duasetapas"),
			    set.getString("foto"),
			    set.getInt("idusuario")
		    ):
		    null;

    }
    
    public static boolean editarSenha(String email, String novaSenha) throws SQLException{
	Sistema.BANCO.run("UPDATE tblUsuario SET senha=? where email=?;", 
		    Sistema.ENCRIPTA.encrypt(novaSenha), 
		    Sistema.ENCRIPTA.encrypt(email)
	    );
	    return true; 
    }
}
