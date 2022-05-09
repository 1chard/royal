package com.royal.dao;

import com.royal.jdbc.MySQL;
import com.royal.model.Recuperacao;
import com.royal.model.Usuario;
import com.royal.Sistema;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
public class RecuperacaoDAO {
    public static boolean gravar(Recuperacao recuperacao){
	try { 
	    Sistema.BANCO.run("INSERT into tblRecuperacao(codigo, idUsuario) values(?, ?);",
		    recuperacao.codigo,
		    recuperacao.idUsuario
	    );
	    return true;
	
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static Recuperacao buscar(int idRecuperacao){
	try{

	    var set = Sistema.BANCO.query("SELECT * from tblRecuperacao where idRecuperacao = ?;", idRecuperacao);
	    
	    return set.next() ?
		    new Recuperacao(
			    set.getInt("codigo"),
			    set.getInt("idRecuperacao"),
			    set.getTimestamp("data"),
			    set.getInt("idUsuario")
			    
		    ):
		    null;
	    
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    public static Recuperacao ativa(String email){
	try{
	    var set = Sistema.BANCO.query("select tblrecuperacao.* from tblrecuperacao "
		    + "inner join tblusuario on tblusuario.idusuario = tblrecuperacao.idusuario "
		    + "where tblusuario.email = ? and tblrecuperacao.data > date_sub(now(), interval 15 minute)"
		    + "order by tblrecuperacao.idrecuperacao desc limit 1", Sistema.ENCRIPTA.encrypt(email));
	    
	    return set.next() ?
		    new Recuperacao(
			    set.getInt("codigo"),
			    set.getInt("idRecuperacao"),
			    set.getTimestamp("data"),
			    set.getInt("idUsuario")
			    
		    ):
		    null;
	    
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
}
