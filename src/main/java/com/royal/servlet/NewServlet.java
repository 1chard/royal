package com.royal.servlet;

import com.royal.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *
 * @author richard
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/teste"})
public class NewServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try ( PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println(Sistema.PESSOAS);
		}
	}

}
