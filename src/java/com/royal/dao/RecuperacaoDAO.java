package com.royal.dao;

import com.royal.jdbc.MySQL;
import com.royal.model.Recuperacao;
import com.royal.model.Usuario;
import com.royal.servlet.Sistema;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
public class RecuperacaoDAO {
    public static boolean gravar(Recuperacao recuperacao) throws SQLException{
	    Sistema.BANCO.run("INSERT into tblRecuperacao(codigo, idUsuario) values(?, ?);", 
		    recuperacao.codigo,
		    recuperacao.idUsuario
	    );
	    return true;
    }
    
    public static Recuperacao buscar(int idRecuperacao) throws SQLException{

	    var set = Sistema.BANCO.query("SELECT * from tblRecuperacao where idRecuperacao = ? order by idusuario desc;", idRecuperacao);
	    
	    return set.next() ?
		    new Recuperacao(
			    set.getInt("codigo"),
			    set.getInt("idRecuperacao"),
			    set.getTimestamp("data"),
			    set.getInt("idUsuario")
			    
		    ):
		    null;

    }
}
