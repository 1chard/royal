package com.royal;

import com.royal.dao.CategoriaDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Categoria;
import com.royal.model.ReceitaUsuario;
import com.royal.model.TipoTransferencia;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author suporte
 */
public class Extra {
    public static <T> T orDefault(T x1, T x2){
	return x1 != null ? x1 : x2;
    }
    
    public static void main(String[] args) throws SQLException {
	int id;
	
	System.out.println(CategoriaDAO.listar());
    }
}
