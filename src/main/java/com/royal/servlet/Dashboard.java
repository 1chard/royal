package com.royal.servlet;

import com.royal.Sistema;
import com.jsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Status;
import com.royal.dao.CategoriaDAO;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Categoria;
import com.royal.model.TipoTransferencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

		String token = req.getParameter("k");

		if (Sistema.PESSOAS.containsKey(token)) {
			var pessoa = Sistema.PESSOAS.get(token).usuario;
			
			try {
				var categorias = CategoriaDAO.listar();
				var despesas = new ArrayList<Categoria>();
				var receitas = new ArrayList<Categoria>();

				categorias.forEach((categoria) -> {
					if (categoria.tipoTransferencia == TipoTransferencia.DESPESA) {
						despesas.add(categoria);
					} else {
						receitas.add(categoria);
					}
				});
				
				var despesaGeral = DespesaUsuarioDAO.despesaGeral(pessoa.id);
				var receitaGeral = ReceitaUsuarioDAO.receitaGeral(pessoa.id);

				JsonStream.serialize(Map.of(
						"saldo", receitaGeral.subtract(despesaGeral),
						"despesa", despesaGeral,
						"receita", receitaGeral,
						"categorias", Map.of(
								"despesas", despesas,
								"receitas", receitas
						)
				), resp.getOutputStream());
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			resp.sendError(404);
		}
	}

}
