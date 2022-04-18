package com.royal.servlet;

import com.royal.API;
import com.royal.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/dashboard"})
public class Dashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");
	
	int httpStatus;
	Status status;
	
	String token = req.getParameter("k");
	
	if(Sistema.PESSOAS.containsKey(token)){ 
	    
	} else {
	    resp.sendError(404);
	}
    }
    
}
