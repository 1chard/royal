package com.royal.dao;

import com.royal.model.Categoria;
import com.royal.Sistema;
import com.royal.model.Categoria.TipoTransferencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suporte
 */
public class CategoriaDAO {


	public static boolean inserir(Categoria categoria) {
	    try {
		Sistema.BANCO.run("INSERT INTO tblCategoria (nome,cor,icone,idTipoTransferencia) VALUES (?,?,?,("
			+ "select idTipoTransferencia from tblTipoTransferencia where nome = ?"
			+ "));",
			categoria.nome,
			categoria.cor,
			categoria.icone,
			categoria.tipoTransferencia.toString()
		);
		return true;
	    } catch (SQLException ex) {
		throw new RuntimeException(ex);
	    }

	}
	
	public static List<Categoria> listar(){
	    try {
		var list = new ArrayList<Categoria>();
		
		var query = Sistema.BANCO.query("SELECT"
			+ " tblCategoria.idcategoria, tblCategoria.nome, tblCategoria.cor, tblCategoria.icone, tblTipoTransferencia.nome as tipo"
			+ " FROM tblCategoria inner join tblTipoTransferencia on tblTipoTransferencia.idTipoTransferencia = tblCategoria.idTipoTransferencia;");
		
		
		
		while (query.next()) {
		    list.add(
			    new Categoria(
				    query.getInt("idcategoria"),
				    query.getString("nome"),
				    query.getString("cor"),
				    query.getString("icone"),
				    TipoTransferencia.valueOf(query.getString("tipo"))
			    )
		    );
		}
		
		return list;
	    } catch (SQLException ex) {
		throw new RuntimeException(ex);
	    }
	}
	private CategoriaDAO() {
	}

}
