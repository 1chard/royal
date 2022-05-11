package com.royal.servlet;

import com.qsoniter.JsonArray;
import com.qsoniter.JsonObject;
import com.royal.Sistema;
import com.qsoniter.output.JsonStream;
import com.royal.API;
import com.royal.Extra;
import com.royal.MapBuilder;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Categoria;
import com.royal.model.Frequencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

			if (args.length == 2) {
				final Object json;

				switch (args[1]) {
					case "saldo" -> {
					    final BigDecimal despesa;
					    final BigDecimal receita;
					    
					    
					    
					    var ano = Extra.parseInteger(req.getParameter("ano"));
					    
					    if(ano != null){
						var mes = Extra.parseInteger(req.getParameter("mes"));
						
						if(mes != null){
						    despesa = DespesaUsuarioDAO.despesaMensal(pessoa.id, ano, mes);
						    receita = ReceitaUsuarioDAO.receitaMensal(pessoa.id, ano, mes);
						} else {
						    despesa = DespesaUsuarioDAO.despesaAnual(pessoa.id, ano);
						    receita = ReceitaUsuarioDAO.receitaAnual(pessoa.id, ano);
						}
					    } else {
						despesa = DespesaUsuarioDAO.despesaGeral(pessoa.id);
						   receita = ReceitaUsuarioDAO.receitaGeral(pessoa.id);
					    }
					    
					    json = new JsonObject().add("despesa", despesa)
						    .add("receita", receita)
						    .add("saldo", receita.subtract(despesa));
					}
					case "categorias" -> {
					    var objeto = new JsonObject();
					    
					    objeto.add("despesas", Categoria.DESPESAS);
					    objeto.add("receitas", Categoria.RECEITAS);
					    
					    json = objeto;
					}
					case "favorito" -> {
					    var lista = new JsonArray();
					    
					    DespesaUsuarioDAO.favoritos(pessoa.id).forEach(despesa -> lista.add(
						    new JsonObject()
						    .add("valor", despesa.valor)
						    .add("data", despesa.data.toString())
						    .add("pendente", despesa.data != null ? despesa.data.toString() : null)
						    .add("anexo", despesa.anexo)
						    .add("descricao", despesa.descricao)
						    .add("observacao", despesa.observacao)
						    .add("favorito", despesa.favorito)
						    .add("inicioRepeticao", despesa.inicioRepeticao != null ? despesa.inicioRepeticao.toString() : null)
						    .add("totalParcelas", despesa.totalParcelas)
						    .add("parcelasPagas", despesa.parcelasPagas)
						    .add("parcelasFixas", despesa.parcelasFixas)
						    .add("nomeFrequencia", despesa.nomeFrequencia != null ? despesa.nomeFrequencia.toString() : null)
						    .add("parcelasFixas", despesa.parcelasFixas)
						    .add("idCategoria", despesa.idCategoria)
					    ));
					    ReceitaUsuarioDAO.favoritos(pessoa.id).forEach(receita -> lista.add(new JsonObject()
						    .add("valor", receita.valor)
						    .add("data", receita.data.toString())
						    .add("pendente", receita.data != null ? receita.data.toString() : null)
						    .add("anexo", receita.anexo)
						    .add("descricao", receita.descricao)
						    .add("observacao", receita.observacao)
						    .add("favorito", receita.favorito)
						    .add("inicioRepeticao", receita.inicioRepeticao != null ? receita.inicioRepeticao.toString() : null)
						    .add("totalParcelas", receita.totalParcelas)
						    .add("parcelasPagas", receita.parcelasPagas)
						    .add("parcelasFixas", receita.parcelasFixas)
						    .add("nomeFrequencia", receita.nomeFrequencia != null ? receita.nomeFrequencia.toString() : null)
						    .add("parcelasFixas", receita.parcelasFixas)
						    .add("idCategoria", receita.idCategoria)
					    ));
					    
					    json = lista;
					}
					case "extrato-mes" -> {
						var lista = new ArrayList<JsonObject>();

						var mes = Integer.parseInt(req.getParameter("mes"));
						var ano = Integer.parseInt( req.getParameter("ano"));
						var categorias = Extra.orDefault(req.getParameterValues("cat"), new String[]{});

							DespesaUsuarioDAO.listarPorMes(pessoa.id, ano, mes, categorias).forEach(despesa -> lista.add(
									new JsonObject()
										.add("data", despesa.data)
										.add("valor", despesa.valor.negate())
										.add("categoria", despesa.idCategoria)
										.add("descricao", despesa.descricao)
							));
							ReceitaUsuarioDAO.listarPorMes(pessoa.id, ano, mes, categorias).forEach(despesa -> lista.add(
									new JsonObject()
										.add("data", despesa.data)
										.add("valor", despesa.valor)
										.add("categoria", despesa.idCategoria)
										.add("descricao", despesa.descricao)
							));
						  Collections.sort(lista, (o1, o2) -> ((java.sql.Date) o2.get("data")).compareTo(((java.sql.Date) o1.get("data"))));
						  
						  lista.forEach(mapa -> {
						      mapa.put("data", mapa.get("data").toString());
						  });
						
						json = lista;
					}
					default -> throw new ServletException("nao tem essa porra");
				}
				
				resp.getWriter().append(json.toString());
			} else {

				
					var despesaGeral = DespesaUsuarioDAO.despesaGeral(pessoa.id);
					var receitaGeral = ReceitaUsuarioDAO.receitaGeral(pessoa.id);

					JsonStream.serialize(Map.of(
							"saldo", receitaGeral.subtract(despesaGeral),
							"despesa", despesaGeral,
							"receita", receitaGeral,
							"categorias", Map.of(
									"despesas", Categoria.DESPESAS,
									"receitas", Categoria.RECEITAS
							)
					), resp.getOutputStream()); 

			}
		} else {
			resp.sendError(404);
		}
	}

}
