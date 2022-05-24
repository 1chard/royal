package com.royal.servlet;

import com.royal.Pair;
import com.royal.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard
 */
@WebServlet(name = "AutoLogin", urlPatterns = {"/autologin"})
public class AutoLogin extends HttpServlet {

	private static final List<Pair<String, Integer>> LISTA = new ArrayList<>();
	
	static{
		try {
			var query = Sistema.BANCO.query("select * from token,idUsuario from autoLogin;");
			
			while (query.next()) {
				LISTA.add(Pair.of(query.getString("token"), query.getInt("idUsuario")));
				
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	

}
