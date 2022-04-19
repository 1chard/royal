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
import com.royal.servlet.Sistema;
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
import java.util.Locale;

/**
 *
 * @author suporte
 */
public class Extra {
    public static void main(String[] args) {
		for(char i = 0; i < 0xff; i++){
			if(isNumero(i) || isLetraMaiuscula(i) || isLetraMinuscula(i) || isCaractereEspecial(i)){
				System.out.print(i);
			}
		}
		
    }
	
	private static boolean isNumero(char ch){
		return ch >= '0' && ch <= '9';
	}
	
	private static boolean isLetraMaiuscula(char ch){
		return (ch >= 'A' && ch <= 'Z') || ch == 'À'|| ch == 'Á'|| ch == 'Â'|| ch == 'Ã'|| ch == 'Ç'|| ch == 'È'|| ch == 'É'|| ch == 'Ê'|| ch == 'Ì'|| ch == 'Í'|| ch == 'Î'|| ch == 'Ò'|| ch == 'Ó'|| ch == 'Ô'|| ch == 'Õ'|| ch == 'Ù'|| ch == 'Ú'|| ch == 'Û';
	}
	
	private static boolean isLetraMinuscula(char ch){
		return (ch >= 'a' && ch <= 'z') || ch == 'à'|| ch == 'á'|| ch == 'â'|| ch == 'ã'|| ch == 'ç'|| ch == 'è'|| ch == 'é'|| ch == 'ê'|| ch == 'ì'|| ch == 'í'|| ch == 'î'|| ch == 'ò'|| ch == 'ó'|| ch == 'ô'|| ch == 'õ'|| ch == 'ù'|| ch == 'ú'|| ch == 'û';
	}
	
	private static boolean isCaractereEspecial(char ch){
		return ch == ' ' || ch == '!'|| ch == '"'|| ch == '#'|| ch == '$'|| ch == '%'|| ch == '&'|| ch == '\''|| ch == '('|| ch == ')'|| ch == '*'|| ch == '+'|| ch == ','|| ch == '-'|| ch == '.'|| ch == '/'|| ch == ':'|| ch == ';'|| ch == '<'|| ch == '='|| ch == '>'|| ch == '?'|| ch == '@'|| ch == '['|| ch == '\\'|| ch == ']'|| ch == '^'|| ch == '_'|| ch == '{'|| ch == '|'|| ch == '}'|| ch == '~'|| ch == '|'|| ch == '¨'|| ch == '°'|| ch == '“'|| ch == '”';
	}
}
