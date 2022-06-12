package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.qsoniter.output.JsonStream;
import com.qsoniter.spi.JsonException;
import com.royal.Status;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @author suporte
 */
@WebServlet(name = "Cadastro", urlPatterns = {"/cadastro"})
public final class Cadastro extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		int httpStatus;
		Status status;

		try {
			var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());

			var emailString = json.get("email").mustBe(ValueType.STRING).asString();
			var senhaString = json.get("senha").mustBe(ValueType.STRING).asString();
			var nomeString = json.get("nome").mustBe(ValueType.STRING).asString();

			if (emailString.length() <= 100 && senhaString.length() <= 100 && nomeString.length() <= 200 && !emailString.isBlank() && !senhaString.isBlank() && !nomeString.isBlank()) {
				if (UsuarioDAO.gravar(
						new Usuario(
								nomeString,
								emailString,
								senhaString,
								false,
								null,
								null
						)
				)) {

					status = Status.OK;
				} else {
					status = Status.EMAIL_REPETIDO;
				}

				httpStatus = 200;
			} else {
				status = Status.CAMPO_INVALIDO;
				httpStatus = 400;
			}
		} catch (JsonException e) {
			status = Status.JSON_INVALIDO;
			httpStatus = 400;
		}

		resp.setStatus(httpStatus);

		resp.getWriter().append(JsonStream.serialize(Map.of("status", status.codigo))).flush();
	}

}
