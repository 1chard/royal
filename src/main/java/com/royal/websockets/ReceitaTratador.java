package com.royal.websockets;

import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.royal.Sistema;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.Frequencia;
import com.royal.model.ReceitaUsuario;
import jakarta.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author suporte
 */
public class ReceitaTratador {
    static void tratador(Any json, Session s, String token) throws SQLException {

	switch (json.get("arg").mustBe(ValueType.STRING).asString()) {
	    case "inserir" ->  {
		var usuario = Sistema.PESSOAS.get(token).usuario;
		
		var valor = BigDecimal.valueOf(json.get("valor").mustBe(ValueType.NUMBER).asDouble());
		var pendente = json.get("pendente");
		var inicioRepeticaoString = json.get("inicioRepeticao").asString();
		var nomeFrequenciaString = json.get("nomeFrequencia").asString();
		
		ReceitaUsuarioDAO.gravar(
			new ReceitaUsuario(
				valor,
				Date.valueOf(json.get("data").mustBe(ValueType.STRING).asString()),
				pendente.valueType() == ValueType.STRING ? Date.valueOf(pendente.asString()) : null,
				json.get("descricao").mustBe(ValueType.STRING).asString(),
				json.get("favorito").mustBe(ValueType.BOOLEAN).asBoolean(),
				usuario.id,
				json.get("idCategoria").mustBe(ValueType.NUMBER).asInt(),
				null,
				null,
				json.get("observacao").asString(),
				inicioRepeticaoString != null ? Date.valueOf(inicioRepeticaoString) : null,
				json.get("totalParcelas").as(Integer.class),
				0,
				false,
				nomeFrequenciaString != null ? Frequencia.valueOf(nomeFrequenciaString) : null
			)
		);

		Dashboard.enviar(token, JsonStream.serialize(Map.of("metodo", "receita", "arg", "adicionar", "valor", valor.doubleValue())));
	    }
	}
    }
}
