package com.royal.servlet;

import com.qsoniter.JsonIterator;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Grafico", urlPatterns = {"/grafico/*"})
public class Grafico extends HttpServlet {

	
	private static List<TransferenciaUsuarioBase> tratadorAnoMesListaDespesa(int id, Integer ano, Integer mes){
		if (ano != null) {
			if (mes != null) {
				return TransferenciaUsuarioDAO.listarDespesasMensal(id, ano, mes);
			} else {
				return TransferenciaUsuarioDAO.listarDespesasAnual(id, ano);
			}
		} else {
			return TransferenciaUsuarioDAO.listarDespesas(id);
		}
	}
	
	private static List<TransferenciaUsuarioBase> tratadorAnoMesListaReceita(int id, Integer ano, Integer mes){
		if (ano != null) {
			if (mes != null) {
				return TransferenciaUsuarioDAO.listarReceitasMensal(id, ano, mes);
			} else {
				return TransferenciaUsuarioDAO.listarReceitasAnual(id, ano);
			}
		} else {
			return TransferenciaUsuarioDAO.listarReceitas(id);
		}
	}

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var args = API.parameters(req);

		String token;
		if (args.length > 0 && Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {
			var point = Sistema.PESSOAS.get(token);

			var modo = Extra.orDefault(req.getParameter("modo"), "categoria");
			var ano = Extra.parseInteger(req.getParameter("ano"));
			var mes = Extra.parseInteger(req.getParameter("mes"));

			final List<TransferenciaUsuarioBase> lista;
			final BiFunction<BigDecimal, BigDecimal, BigDecimal> algoritmo;

			switch (args[0]) {
				case "despesa" -> {
					algoritmo = BigDecimal::subtract;
					lista = tratadorAnoMesListaDespesa(point.usuario.id, ano, mes);
				}
				case "receita" -> {
					algoritmo = BigDecimal::add;
					lista = tratadorAnoMesListaReceita(point.usuario.id, ano, mes);
				}
				default -> {
					resp.sendError(400);
					System.out.println("testess");
						
					return;
					
				}
			}
			
			resp.getWriter().append(
					switch(modo){
						case "categoria" -> {
							var json = new HashMap<String, BigDecimal>();
							
							lista.forEach(receita -> {
								var nome = Integer.toString(receita.idCategoria);

								var decimal = json.getOrDefault(nome, BigDecimal.ZERO);

								json.put(nome, algoritmo.apply(decimal, receita.valor));
							});
							
							yield JsonStream.serialize(json);
						}
						case "lista" -> {
							final String periodo = req.getParameter("periodo");
							
							final java.util.Date dataFim;
							
							final List<BigDecimal> dados;
							
							//torna mais rapido de procurar, teoricamente
							Collections.sort(lista, (o1, o2) -> {
								return o1.data.compareTo(o2.data);
							});
							
							
							yield JsonStream.serialize(switch (periodo){
								case "dia-mes" -> {
									assert ano != null;
									assert mes != null;
									
									yield tratador(new GregorianCalendar(ano, mes - 1, 1), new GregorianCalendar(ano, mes, 1), Calendar.DAY_OF_MONTH, algoritmo, lista);		
								}
								case "semana-mes" -> {
									assert ano != null;
									assert mes != null;
									
									yield tratador(new GregorianCalendar(ano, mes - 1, 1), new GregorianCalendar(ano, mes, 1), Calendar.WEEK_OF_MONTH, algoritmo, lista);	
								}
								case "mes-ano" -> {
									assert ano != null;
									
									yield tratador(new GregorianCalendar(ano, 0, 1), new GregorianCalendar(ano + 1, 0, 1), Calendar.MONTH, algoritmo, lista);	
								}
								default -> {
								    System.out.println("teste");
									resp.sendError(400);
									yield "";
								}
							});
						}
						default -> {
						    
								    System.out.println("teste432");
							resp.sendError(400);
							yield "";
						}
					}
			).flush();

		} else {
			resp.sendError(404);
		}
	}
	
	public static List<BigDecimal> tratador(GregorianCalendar inicio, GregorianCalendar fim, int tipoDado, BiFunction<BigDecimal,BigDecimal,BigDecimal> algoritmo, List<TransferenciaUsuarioBase> lista){
	    var retorno = new ArrayList<BigDecimal>();
	    
	    int indexTransferencia = 0;
										
									for(; inicio.before(fim); inicio.add(tipoDado, 1)){
										BigDecimal atual = BigDecimal.ZERO;
										
										var argumentoData = inicio.get(tipoDado);
										
										while(indexTransferencia < lista.size()){
											var calendarioTransferencia = new GregorianCalendar(0, Calendar.JANUARY, 1);
											var transf = lista.get(indexTransferencia);
											
											calendarioTransferencia.setTime(transf.data);
											
											if(argumentoData == calendarioTransferencia.get(tipoDado)){
												atual = algoritmo.apply(atual, transf.valor);
												indexTransferencia++;
											} else {
												break; // n vai ter nenhum transf nesse dia
											}
										}
										
										retorno.add(atual);
									}
									
									return retorno;
	}

}
