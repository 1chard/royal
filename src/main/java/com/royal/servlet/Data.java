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
@WebServlet(name = "Data", urlPatterns = {"/data/*"})
public class Data extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");
	
	var argumentos = API.parameters(req);
	int httpStatus;
	Status status;
	
	String token = req.getParameter("key");
	
	if(argumentos.length == 1 && Sistema.SESSOES.containsKey(token)){ 
	    
	    switch(argumentos[0]){
		
	    }
	    
	    resp.getWriter().append("teste =" +req.getAttributeNames()).flush();
	} else {
	    resp.sendError(404);
	}
    }
    
}
