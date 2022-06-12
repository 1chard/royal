package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.qsoniter.any.Any;
import com.qsoniter.output.JsonStream;
import com.qsoniter.spi.JsonException;
import com.royal.Sistema;
import com.royal.Status;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.model.Frequencia;
import com.royal.model.TransferenciaUsuario;
import com.royal.websockets.Dashboard;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

/**
 * @author richard
 */
@WebServlet(name = "Transferencia", urlPatterns = {"/transferencia"})
public class Transferencia extends HttpServlet {
	private static final String DESPESA_RESPOSTA = JsonStream.serialize(Map.of("metodo", "despesa", "arg", "remover"));
	private static final String RECEITA_RESPOSTA = JsonStream.serialize(Map.of("metodo", "receita", "arg", "adicionar"));

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		int httpStatus;
		Status status;
		String token;

		if (Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {
			try {
				var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());
				var valorAny = json.get("metodo");

				if (valorAny.valueType() == ValueType.STRING) {
					switch (valorAny.asString()) {
						case "despesa" -> {
							trans(json, json.get("valor").mustBe(ValueType.NUMBER).asBigDecimal().negate(), token, DESPESA_RESPOSTA);
							status = Status.OK;
							httpStatus = 200;
						}
						case "receita" -> {
							trans(json, json.get("valor").mustBe(ValueType.NUMBER).asBigDecimal(), token, RECEITA_RESPOSTA);
							status = Status.OK;
							httpStatus = 200;
						}
						default -> {
							status = Status.REQUISICAO_INVALIDA;
							httpStatus = 400;
						}
					}
				} else {
					httpStatus = 400;

					status = valorAny.valueType() == com.qsoniter.ValueType.INVALID ?
							Status.CAMPO_INVALIDO : Status.CAMPO_TIPO_INCORRETO;
				}

			} catch (JsonException e) {
				status = Status.JSON_INVALIDO;
				httpStatus = 400;
			}

		} else {
			resp.sendError(404);
			return;
		}

		resp.setStatus(httpStatus);
		resp.getWriter().append(Map.of("status", status.codigo).toString()).flush();
	}

	private static void trans(Any json, BigDecimal valor, String token, String resposta) {
		var usuario = Sistema.PESSOAS.get(token).usuario;
		var nomeFrequenciaString = json.get("frequencia").asString();
		var data = Date.valueOf(json.get("data").mustBe(ValueType.STRING).asString());

		TransferenciaUsuarioDAO.gravar(
				new TransferenciaUsuario(
						valor,
						data,
						json.get("descricao").mustBe(ValueType.STRING).asString(),
						json.get("favorito").mustBe(ValueType.BOOLEAN).asBoolean(),
						json.get("parcelada").mustBe(ValueType.BOOLEAN).asBoolean(),
						json.get("fixa").mustBe(ValueType.BOOLEAN).asBoolean(),
						usuario.id,
						json.get("idCategoria").mustBe(ValueType.NUMBER).asInt(),
						null,
						json.get("anexo").asString(),
						json.get("observacao").asString(),
						nomeFrequenciaString != null ? Frequencia.valueOf(nomeFrequenciaString) : null,
						json.get("totalParcelas").asInt()
				)
		);

		Dashboard.enviar(token, resposta);
	}

}
