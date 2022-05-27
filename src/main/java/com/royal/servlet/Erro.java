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


	public static String enviador(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String pst = sw.toString();

		Mail.enviar(t.getClass().getName(), pst, "richardcutrim01@gmail.com");

		return pst;
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var string = enviador((Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
		resp.getWriter().append(string).flush();
	}

}
