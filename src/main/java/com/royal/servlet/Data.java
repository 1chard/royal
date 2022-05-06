package com.royal.servlet;

import com.royal.Sistema;
import com.qsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Extra;
import com.royal.MapBuilder;
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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/data/*"})
public class Data extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var args = API.parameters(req);

		String token;

		if (args.length >= 1 && Sistema.PESSOAS.containsKey((token = args[0]))) {
			var pessoa = Sistema.PESSOAS.get(token).usuario;

			if (args.length >= 2) {
				final Object json;

				switch (args[1]) {
					case "saldo" -> {

					}
					case "categorias" -> {

					}
					case "extrato" -> {
						var lista = new ArrayList<Map<String, Object>>();

						var mes = Extra.orDefault(Integer.parseInt(req.getParameter("mes")), Sistema.CALENDARIO.get(Calendar.MONTH) + 1);
						var ano = Extra.orDefault(Integer.parseInt(req.getParameter("ano")), Sistema.CALENDARIO.get(Calendar.YEAR));

						try {
							DespesaUsuarioDAO.listarPorMes(pessoa.id, ano, mes).forEach(despesa -> lista.add(
									new MapBuilder()
											.add("data", despesa.data.toString())
											.add("valor", despesa.valor)
											.add("categoria", despesa.idCategoria)
											.build()
							));
						} catch (SQLException ex) {
							Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			} else {

				try {
					var despesas = new ArrayList<Map<String, Object>>();
					var receitas = new ArrayList<Map<String, Object>>();

					Categoria.DESPESAS.forEach(despesa -> despesas.add(
							Map.of("idCategoria", despesa.idCategoria,
									"nome", despesa.nome,
									"cor", despesa.cor,
									"icone", despesa.icone)
					));

					Categoria.RECEITAS.forEach(despesa -> receitas.add(
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

			}
		} else {
			resp.sendError(404);
		}
	}

}