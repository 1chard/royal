package com.royal.servlet;

import com.qsoniter.JsonArray;
import com.qsoniter.JsonObject;
import com.royal.Sistema;
import com.qsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Extra;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Collections;
import java.util.Date;

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

		if (Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {
			var pessoa = Sistema.PESSOAS.get(token).usuario;

			var retornos = new Object[args.length];

			for (int i = 0, length = args.length; i < length; i++) {
				final Object json;

				switch (args[i]) {
					case "saldo" -> {
						final BigDecimal despesa;
						final BigDecimal receita;

						var ano = Extra.parseInteger(req.getParameter("ano"));

						if (ano != null) {

							var mes = Extra.parseInteger(req.getParameter("mes"));

							if (mes != null) {
								despesa = TransferenciaUsuarioDAO.despesaLiquidaMensal(pessoa.id, ano, mes);
								receita = TransferenciaUsuarioDAO.receitaLiquidaMensal(pessoa.id, ano, mes);
							} else {
								despesa = TransferenciaUsuarioDAO.despesaLiquidaAnual(pessoa.id, ano);
								receita = TransferenciaUsuarioDAO.receitaLiquidaAnual(pessoa.id, ano);
							}
						} else {
							despesa = TransferenciaUsuarioDAO.despesaLiquidaGeral(pessoa.id);
							receita = TransferenciaUsuarioDAO.receitaLiquidaGeral(pessoa.id);
						}

						json = new JsonObject().add("despesa", despesa)
								.add("receita", receita)
								.add("saldo", receita.subtract(despesa));
					}
					case "saldo-geral" -> {
						json = TransferenciaUsuarioDAO.saldoLiquidoGeral(pessoa.id);
					}
					case "categorias" -> {
						var objeto = new JsonObject();

						objeto.add("despesas", Categoria.DESPESAS);
						objeto.add("receitas", Categoria.RECEITAS);

						json = objeto;
					}
					case "favorito" -> {
						var lista = new JsonArray();

						TransferenciaUsuarioDAO.favoritos(pessoa.id).forEach(despesaP -> {
							var despesa = despesaP.first;

							lista.add(
									new JsonObject()
											.add("valor", despesa.valor)
											.add("data", despesa.data.toString())
											.add("anexo", despesa.anexo)
											.add("descricao", despesa.descricao)
											.add("observacao", despesa.observacao)
											.add("parcelada", despesa.parcelada)
											.add("fixa", despesa.fixa)
											.add("nomeFrequencia", despesa.frequencia != null ? despesa.frequencia.toString() : null)
											.add("categoria", despesa.idCategoria)
											.add("parcelas", despesaP.second)
							);
						});

						json = lista;
					}
					case "perfil" -> {
						json = new JsonObject()
								.add("nome", pessoa.nome)
								.add("email", pessoa.email)
								.add("duasetapas", pessoa.duasetapas)
								.add("foto", pessoa.foto);
					}
					case "extrato-mes" -> {
						var lista = new ArrayList<JsonObject>();

						var mes = Integer.parseInt(req.getParameter("mes"));
						var ano = Integer.parseInt(req.getParameter("ano"));
//			var categorias = Extra.orDefault(req.getParameterValues("cat"), new String[0]);

						TransferenciaUsuarioDAO.listarMensal(pessoa.id, ano, mes).forEach(despesa -> lista.add(
								new JsonObject()
										.add("data", despesa.data)
										.add("valor", despesa.valor)
										.add("categoria", despesa.idCategoria)
										.add("descricao", despesa.descricao)
										.add("indice", despesa.indice)
										.add("parcelas", despesa.parcelas)
						));
						Collections.sort(lista, (o1, o2) -> ((java.sql.Date) o2.get("data")).compareTo(((Date) o1.get("data"))));

						lista.forEach(mapa -> {
							mapa.put("data", mapa.get("data").toString());
						});

						json = lista;
					}
					default -> {
						resp.sendError(400);
						return;
					}
				}

				retornos[i] = json;
			}

			if (retornos.length == 1) {
				resp.getWriter().append(retornos[0].toString()).flush();
			} else {
				resp.getWriter().append(Arrays.toString(retornos)).flush();
			}
		} else {
			resp.sendError(404);
		}
	}

}
