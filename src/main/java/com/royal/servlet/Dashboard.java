package com.royal.servlet;

import com.jsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Status;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	if(Sistema.PESSOAS.containsKey(token) ){ 
	    var pessoa = Sistema.PESSOAS.get(token).usuario;
	    var calendario = Calendar.getInstance();
	    
	    try {
		JsonStream.serialize(Map.of(
			"saldo", pessoa.saldo,
			"despesa", DespesaUsuarioDAO.despesaMensal(pessoa.id, calendario.get(Calendar.MONTH) + 1, calendario.get(Calendar.YEAR)),
			"receita", ReceitaUsuarioDAO.despesaMensal(pessoa.id, calendario.get(Calendar.MONTH) + 1, calendario.get(Calendar.YEAR))
		), resp.getOutputStream());
	    } catch (SQLException ex) {
		throw new RuntimeException(ex);
	    }
	} else {
	    resp.sendError(404);
	}
    }
    
}
