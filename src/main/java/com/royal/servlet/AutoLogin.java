package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.JsonObject;
import com.qsoniter.output.JsonStream;
import com.qsoniter.spi.JsonException;
import com.qsoniter.spi.TypeMismatchException;
import com.royal.Sistema;
import com.royal.Status;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author richard
 */
@WebServlet(name = "AutoLogin", urlPatterns = {"/autologin"})
public class AutoLogin extends HttpServlet {

	private static final Map<String, Integer> MAP_MAP = new HashMap<>();

	static {
		try {
			var query = Sistema.BANCO.query("select token, idUsuario from autoLogin;");

			while (query.next()) {
				MAP_MAP.put(query.getString("token"), query.getInt("idUsuario"));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}


	public static void ativarAutoLogin(String token, int idUsuario) {
		try {
			Sistema.BANCO.run("INSERT INTO autoLogin(token, idUsuario) values(?, ?);", token, idUsuario);

			synchronized (AutoLogin.class) {
				MAP_MAP.put(token, idUsuario);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int httpStatus;
		Status status;
		var resposta = new JsonObject();

		try {
			var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());


			String token = json.get("token").asString();

			Integer id;
			synchronized (AutoLogin.class) {
				id = MAP_MAP.get(token);
			}

			if (id != null) {


				var usuario = UsuarioDAO.buscar(id);
				TransferenciaUsuarioDAO.atualizarFixas(id);

				var sessao = new Sistema.Sessao();

				sessao.usuario = usuario;

				Sistema.PESSOAS.put(token, sessao);

				status = Status.OK;
				httpStatus = 200;

			} else {
				status = Status.REQUISICAO_INVALIDA;
				httpStatus = 400;
			}
		} catch (TypeMismatchException e) {
			status = Status.CAMPO_TIPO_INCORRETO;
			httpStatus = 400;
		} catch (JsonException e) {
			status = Status.JSON_INVALIDO;
			httpStatus = 400;
		}

		resp.setStatus(httpStatus);
		resposta.put("status", status.codigo);

		resp.getWriter().append(JsonStream.serialize(resposta)).flush();
	}


}
