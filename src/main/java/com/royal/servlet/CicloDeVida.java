package com.royal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import static com.royal.Sistema.BANCO;

/**
 * @author suporte
 */

@WebServlet(loadOnStartup = 1)

public class CicloDeVida extends HttpServlet {


	@Override
	public void destroy() {
		try {
			BANCO.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public void init() {
	}
}
