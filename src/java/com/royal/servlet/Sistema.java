package com.royal.servlet;

import com.royal.Cripto;
import com.royal.jdbc.MariaDB;
import com.royal.jdbc.MySQL;
import com.royal.jdbc.SQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard
 */
@WebServlet(loadOnStartup = 1)
public class Sistema extends HttpServlet {

    private static final String HOST = "localhost:3306";
    private static final String DATABASE = "royal";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String CHAVE = "cumonista";
    
    public static final Cripto ENCRIPTA = Cripto.Encrypter.of(CHAVE);
    public static final Cripto DESENCRIPTA = Cripto.Decrypter.of(CHAVE);
    public static final SQL BANCO = new MySQL(HOST, DATABASE, USER, PASSWORD);
    public static final Map<String, Sessao> sessoes = new HashMap<>();

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {
	try {
	    BANCO.close();
	} catch (Exception ex) {
	    throw new RuntimeException(ex);
	}

    }

    public static class Sessao {

	public jakarta.servlet.http.HttpSession httpSession;
	public jakarta.websocket.Session socketSession;
	public Map<String, Object> objetos = new HashMap<>();

	@Override
	public int hashCode() {
	    return Objects.hash(httpSession, socketSession, objetos);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
		return true;
	    } else if (obj instanceof Sessao) {
		return false;
	    } else {
		final Sessao other = (Sessao) obj;
		return Objects.equals(this.httpSession, other.httpSession) && Objects.equals(this.socketSession, other.socketSession) && Objects.equals(this.objetos, other.objetos);
	    }

	}
    }
}
