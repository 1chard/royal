package com.royal.websockets;

import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.royal.Sistema;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.model.ReceitaUsuario;
import jakarta.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author suporte
 */
public class ReceitaTratador {
    static void tratador(Any json, Session s, String token) throws SQLException {

	switch (json.get("arg").mustBe(ValueType.STRING).asString()) {
	    case "inserir": {
		var valor = BigDecimal.valueOf(json.get("valor").mustBe(ValueType.NUMBER).asDouble());
		var usuario = Sistema.PESSOAS.get(token).usuario;
		var pendente = json.get("pendente");
		
		ReceitaUsuarioDAO.gravar(
			new ReceitaUsuario.Builder(
				valor,
				Date.valueOf(json.get("data").mustBe(ValueType.STRING).asString()),
				pendente.valueType() == ValueType.STRING ? Date.valueOf(pendente.asString()) : null,
				json.get("descricao").mustBe(ValueType.STRING).asString(),
				json.get("favorito").mustBe(ValueType.BOOLEAN).asBoolean(),
				usuario.id,
				json.get("idCategoria").mustBe(ValueType.NUMBER).asInt()
			).build()
		);

		Dashboard.SESSOES.get(token).forEach(sessao -> {
		    synchronized (sessao) {
			try {
			    sessao.getBasicRemote().sendText(JsonStream.serialize(Map.of("metodo", "despesa", "arg", "remover", "valor", valor.doubleValue())));
			} catch (IOException ex) {
			    throw new RuntimeException(ex);
			}
		    }
		});
		
		break;
	    }
	    case "lista-mes": {
		
	    }
	}
    }
}
