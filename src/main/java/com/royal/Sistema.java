package com.royal;

import com.royal.Cripto;
import com.royal.jdbc.MariaDB;
import com.royal.jdbc.MySQL;
import com.royal.jdbc.SQL;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard
 */
@WebServlet(loadOnStartup = 1)
public class Sistema{

    private static final String HOST;
    private static final String DATABASE;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CHAVE;
	
	static {
		switch (System.getProperty("user.name")){
			case "richard": {
				HOST = "localhost:3306";
				DATABASE = "royal";
				USER = "richard";
				PASSWORD = "123";
				CHAVE = "pOrQuEaTeRrAéPlAnA";
				break;
			}
			case "suporte": {
				HOST = "localhost:3306";
				DATABASE = "royal";
				USER = "root";
				PASSWORD = "12345678";
				CHAVE = "cumonista";
				break;
			}
			case "SERVIÇO LOCAL": {
				HOST = "localhost:3308";
				DATABASE = "royal";
				USER = "royal";
				PASSWORD = "royal";
				CHAVE = "alemanhaSemprePerde";
				break;
			}
			default: {
				throw new RuntimeException();
			}
		}
		
		try{
			BANCO = new MySQL(HOST, DATABASE, USER, PASSWORD);
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

    
    public static final Cripto ENCRIPTA = Cripto.Encrypter.of(CHAVE);
    public static final Cripto DESENCRIPTA = Cripto.Decrypter.of(CHAVE);
    public static final SQL BANCO;
    public static final Map<String, Sessao> PESSOAS = new HashMap<>();

    private static final long serialVersionUID = 1L;

    
    public static class Sessao {
	public Map<String, Object> objetos = new HashMap<>();
	public Usuario usuario;
	public List<jakarta.servlet.http.HttpSession> sessoes = new ArrayList<>();
    }
}
