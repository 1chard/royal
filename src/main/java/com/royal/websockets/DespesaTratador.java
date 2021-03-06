package com.royal.websockets;

import com.qsoniter.ValueType;
import com.qsoniter.any.Any;
import com.qsoniter.output.JsonStream;
import com.royal.Sistema;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.model.Frequencia;
import com.royal.model.TransferenciaUsuario;
import jakarta.websocket.Session;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * @author suporte
 */
public class DespesaTratador {

	static void tratador(Any json, Session s, String token) {

		switch (json.get("arg").mustBe(ValueType.STRING).asString()) {
			case "inserir" -> {
				var usuario = Sistema.PESSOAS.get(token).usuario;

				var valor = BigDecimal.valueOf(json.get("valor").mustBe(ValueType.NUMBER).asDouble());
				var nomeFrequenciaString = json.get("frequencia").asString();
				var data = Date.valueOf(json.get("data").mustBe(ValueType.STRING).asString());

				TransferenciaUsuarioDAO.gravar(
						new TransferenciaUsuario(
								valor.negate(),
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

				var calendario = new GregorianCalendar();
				calendario.setTime(data);

				Dashboard.enviar(token, JsonStream.serialize(
						Map.of("metodo", "despesa", "arg", "remover")
				));
			}

		}
	}

	private DespesaTratador() {
	}


}
