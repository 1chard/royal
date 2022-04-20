package com.royal.servlet;

import static com.royal.Sistema.BANCO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author suporte
 */

@WebServlet(loadOnStartup = 1)

public class CicloDeVida extends HttpServlet {

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
}
