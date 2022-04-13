package com.royal.servlet;

import com.royal.external.Mail;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Erro", urlPatterns = {"/erro"})
public class Erro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	var exception = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
	
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	exception.printStackTrace(pw);
	String pst = sw.toString();
	
	resp.getWriter().append(pst).flush();
	
	Mail.enviar(exception.getClass().getName(), pst, "richardcutrim01@gmail.com");
    }
    
}
