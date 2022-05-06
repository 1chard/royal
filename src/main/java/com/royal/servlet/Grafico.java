package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Sistema;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Categoria;
import com.royal.model.DespesaUsuario;
import com.royal.model.ReceitaUsuario;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
		try {
		    resp.getWriter().append(switch (args[1]) {
			case "despesa" ->
			    despesaTrata(args, point.usuario);
			case "receita" ->
			    receitaTrata(args, point.usuario);
			default -> {
			    resp.sendError(400);
			    throw new RuntimeException("veio nem um nem outro");
			}
		    }).flush();

		} catch (SQLException e) {
		    throw new ServerException(e.getSQLState(), e);
		}
	    } else {
		resp.sendError(404);
	    }
	} else {
	    resp.sendError(404);
	}
    }

    private static String despesaTrata(String[] args, Usuario usuario) throws IOException, SQLException {
	var json = new HashMap<String, BigDecimal>();
	final List<DespesaUsuario> lista;

	if (args.length >= 3) {
	    int ano = Integer.parseInt(args[2]);

	    if (args.length >= 4) {
		int mes = Integer.parseInt(args[3]);

		lista = DespesaUsuarioDAO.listarPorMes(usuario.id, ano, mes);

	    } else {

		lista = DespesaUsuarioDAO.listarPorAno(usuario.id, ano);

	    }
	} else {
	    lista = DespesaUsuarioDAO.listar(usuario.id);
	}
	
	lista.forEach(despesa -> {
	    var nome = Integer.toString(despesa.idCategoria);

	    var decimal = json.getOrDefault(nome, BigDecimal.ZERO);

	    json.put(nome, decimal.add(despesa.valor));
	});

	return JsonStream.serialize(json);
    }

    private static String receitaTrata(String[] args, Usuario usuario) throws IOException, SQLException {
	var json = new HashMap<String, BigDecimal>();
	final List<ReceitaUsuario> lista;

	if (args.length >= 3) {
	    int ano = Integer.parseInt(args[2]);

	    if (args.length >= 4) {
		int mes = Integer.parseInt(args[3]);

		lista = ReceitaUsuarioDAO.listarPorMes(usuario.id, ano, mes);

	    } else {

		lista = ReceitaUsuarioDAO.listarPorAno(usuario.id, ano);

	    }
	} else {
	    lista = ReceitaUsuarioDAO.listar(usuario.id);
	}

	lista.forEach(receita -> {
	    var nome = Integer.toString(receita.idCategoria);

	    var decimal = json.getOrDefault(nome, BigDecimal.ZERO);

	    json.put(nome, decimal.add(receita.valor));
	});

	return JsonStream.serialize(json);
    }

}
