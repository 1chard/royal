package com.royal;

import com.royal.dao.CategoriaDAO;
import com.royal.model.Categoria;
import com.royal.model.TipoTransferencia;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author suporte
 */
public class Extra {
    static class Teste{
	public BigDecimal as = BigDecimal.ONE;
    }
    
    public static void main(String[] args) throws SQLException {
	CategoriaDAO.inserir(new Categoria(Integer.BYTES, "Gemas", "00FF00", "school", TipoTransferencia.RECEITA));
    }
}
