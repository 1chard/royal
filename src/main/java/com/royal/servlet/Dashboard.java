package com.royal.servlet;

import com.royal.Sistema;
import com.jsoniter.output.JsonStream;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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
				var despesas = new ArrayList<Map<String, Object>>();
				var receitas = new ArrayList<Map<String, Object>>();

				
//    public Integer idCategoria;
//    public String nome;
//    public String cor;
//    public String icone;
				
						   Categoria.DESPESAS.forEach(despesa -> despesas.add(
							Map.of("idCategoria", despesa.idCategoria,
								"nome", despesa.nome,
								"cor", despesa.cor,
								"icone", despesa.icone)
						   ));
						   
						   Categoria.RECEITAS.forEach(despesa -> despesas.add(
							Map.of("idCategoria", despesa.idCategoria,
								"nome", despesa.nome,
								"cor", despesa.cor,
								"icone", despesa.icone)
						   ));
				
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
