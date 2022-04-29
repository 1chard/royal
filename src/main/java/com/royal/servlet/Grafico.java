package com.royal.servlet;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Sistema;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Grafico", urlPatterns = {"/grafico/*"})
public class Grafico extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	var args = API.parameters(req);

	if (args.length >= 2) {
	    var point = Sistema.PESSOAS.get(args[0]);

	    if (point != null) {
		resp.getWriter().append(switch (args[1]) {
		    case "despesa" -> despesaTrata(args, point.usuario, req, resp);
		    case "receita" -> receitaTrata(args, point.usuario, req, resp);
		    default -> throw new RuntimeException();
		}).flush();

	    } else {
		resp.sendError(404);
	    }
	} else {
	    resp.sendError(404);
	}
    }

    private static String despesaTrata(String[] args, Usuario usuario, HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
	var json = new HashMap<String, BigDecimal>();
	
	if (args.length >= 3) {
	    int ano = Integer.parseInt(args[2]);

	    if (args.length >= 4) {
		int mes = Integer.parseInt(args[3]);

		

		try {
		    DespesaUsuarioDAO.listarPorMes(usuario.id, ano, mes)
			    .forEach(despesa -> {
				System.out.println(despesa.valor);
				
				var numero = String.valueOf(despesa.idCategoria);
				
				var decimal = json.getOrDefault(numero, BigDecimal.ZERO);
				
				json.put(numero, decimal.add(despesa.valor));
			    });
		} catch (SQLException ex) {
		   throw new RuntimeException(ex);
		}
	    }
	}
	
	return JsonStream.serialize(json);
    }
    
    private static String receitaTrata(String[] args, Usuario usuario, HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
	var json = new HashMap<String, BigDecimal>();
	
	if (args.length >= 3) {
	    int ano = Integer.parseInt(args[2]);

	    if (args.length >= 4) {
		int mes = Integer.parseInt(args[3]);

		

		try {
		    ReceitaUsuarioDAO.listarPorMes(usuario.id, ano, mes)
			    .forEach(receita -> {
				System.out.println(receita.valor);
				
				var numero = String.valueOf(receita.idCategoria);
				
				var decimal = json.getOrDefault(numero, BigDecimal.ZERO);
				
				json.put(numero, decimal.add(receita.valor));
			    });
		} catch (SQLException ex) {
		   throw new RuntimeException(ex);
		}
	    }
	}
	
	return JsonStream.serialize(json);
    }

}
