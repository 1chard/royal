package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.royal.Sistema;
import com.royal.websockets.DespesaTratador;
import com.royal.websockets.ReceitaTratador;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.qsoniter.any.Any;


/**
 *
 * @author richard
 */
@WebServlet(name = "Transferencia", urlPatterns = {"/transferencia"})
public class Transferencia extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());
		
		 String token;

        if (Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {
		
		switch (json.get("metodo").mustBe(ValueType.STRING).asString()) {
            case "despesa" -> despesa(json, token);
            case "receita" -> receita(json, token);
            default -> throw new IllegalArgumentException();
        }
		
		} else {
			resp.sendError(404);
		}

	}
	
	private static void despesa(Any json, String token){
		
	}
	
	private static void receita(Any json, String token){
		
	}
	

}
