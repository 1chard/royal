package com.royal;

import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.output.JsonStream;
import com.royal.dao.CategoriaDAO;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Categoria;
import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.model.TipoTransferencia;
import com.royal.model.Usuario;
import com.royal.validation.Senha;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
