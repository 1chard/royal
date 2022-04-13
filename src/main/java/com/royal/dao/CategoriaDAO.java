package com.royal.dao;


import com.royal.model.Categoria;
import com.royal.servlet.Sistema;
import java.sql.SQLException;


/**
 *
 * @author suporte
 */
public class CategoriaDAO {
    private CategoriaDAO(){}
    
    public static boolean inserir(Categoria categoria) throws SQLException{
	Sistema.BANCO.run("INSERT INTO tblCategoria (nome,cor,icone,idTipoTransferencia) VALUES (?,?,?,("
		+ "select idTipoTransferencia from tblTipoTransferencia where nome = ?"
		+ "));", 
		categoria.nome,
		categoria.cor,
		categoria.icone,
		categoria.tipoTransferencia.toString()
	);
    
	return true;
    }
    
    
    
    
}
