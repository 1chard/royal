package com.royal;

import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.output.JsonStream;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.model.Usuario;
import com.royal.servlet.Sistema;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author suporte
 */
public class Extra {
    private Extra(){}
    
    public static void main(String[] args) throws SQLException {
		com.jsoniter.spi.
	DespesaUsuarioDAO.gravar(new DespesaUsuario.DespesaUsuarioBuilder(BigDecimal.ZERO, new Date(System.currentTimeMillis()), true, "", true, 0, 0).build());
    }
}
