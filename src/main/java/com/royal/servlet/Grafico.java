package com.royal.servlet;

import com.qsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Extra;
import com.royal.Sistema;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.model.TransferenciaUsuario;
import com.royal.model.TransferenciaUsuarioBase;
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

/**
 *
 * @author suporte
 */
@WebServlet(name = "Grafico", urlPatterns = {"/grafico/*"})
public class Grafico extends HttpServlet {


	private static String despesaTrata(HttpServletRequest req, Usuario usuario) {
		var json = new HashMap<String, BigDecimal>();
		final List<TransferenciaUsuarioBase> lista;

		var ano = Extra.parseInteger(req.getParameter("ano"));

		if (ano != null) {

			var mes = Extra.parseInteger(req.getParameter("mes"));

			if (mes != null) {

				lista = TransferenciaUsuarioDAO.listarDespesasMensal(usuario.id, ano, mes);

			} else {

				lista = TransferenciaUsuarioDAO.listarDespesasAnual(usuario.id, ano);

			}
		} else {
			lista = TransferenciaUsuarioDAO.listarDespesas(usuario.id);
		}

		lista.forEach(despesa -> {
			var nome = Integer.toString(despesa.idCategoria);

			var decimal = json.getOrDefault(nome, BigDecimal.ZERO);

			json.put(nome, decimal.subtract(despesa.valor));
		});

		return JsonStream.serialize(json);
	}

	private static String receitaTrata(HttpServletRequest req, Usuario usuario) {
		var json = new HashMap<String, BigDecimal>();
		final List<TransferenciaUsuarioBase> lista;

		var ano = Extra.parseInteger(req.getParameter("ano"));

		if (ano != null) {

			var mes = Extra.parseInteger(req.getParameter("mes"));

			if (mes != null) {

				lista = TransferenciaUsuarioDAO.listarReceitasMensal(usuario.id, ano, mes);

			} else {

				lista = TransferenciaUsuarioDAO.listarReceitasAnual(usuario.id, ano);

			}
		} else {
			lista = TransferenciaUsuarioDAO.listarReceitas(usuario.id);
		}

		lista.forEach(receita -> {
			var nome = Integer.toString(receita.idCategoria);

			var decimal = json.getOrDefault(nome, BigDecimal.ZERO);

			json.put(nome, decimal.add(receita.valor));
		});

		return JsonStream.serialize(json);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var args = API.parameters(req);
		
		String token;
		if (args.length > 0 && Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {
			var point = Sistema.PESSOAS.get(token);
			
			resp.getWriter().append(switch (args[0]) {
				case "despesa" ->
						despesaTrata(req, point.usuario);
				case "receita" ->
						receitaTrata(req, point.usuario);
				default -> {
					resp.sendError(400);
					yield "";
				}
			}).flush();
			
		} else {
			resp.sendError(404);
		}
	}

}
