package com.royal.websockets;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.royal.model.DespesaUsuario;
import com.royal.servlet.Sistema;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author richard
 */
@ServerEndpoint("/dashboard/{token}")
public class Dashboard {

	public final static Map<String, Session> SESSOES = Collections.synchronizedMap(new HashMap<>());

	@OnOpen
	public void abrir(Session s, @PathParam("token") String token) throws IOException {
		var pessoa = Sistema.PESSOAS.get(token);

		if (pessoa == null) {
			s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Não posso permitir seu login"));
		}

		synchronized (s) {
			s.getBasicRemote().sendText(JsonStream.serialize(Map.of("saldo", pessoa.usuario.saldo)));
		}

		SESSOES.put(token, s);
	}

	@OnMessage
	public void mensagem(String mensagem, Session s) {

		var json = JsonIterator.deserialize(mensagem);

		System.out.println(json);

		switch (json.get("metodo").asString()) {
			case "despesa":
				DespesaData.tratador(json, s);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	@OnClose
	public void fechar(Session s) {

	}
}

class DespesaData {

	private DespesaData() {
	}

	static void tratador(Any json, Session s) {
		switch (json.asString("arg")) {
//	    case "inserir": {
//		DateFormat.
//		var despesa = new DespesaUsuario.Builder(
//			BigDecimal.valueOf(json.get("valor").asDouble()),
//			D
//			, true, descricao, true, 0, 0)
//	    }
//	    break;
//	    default:}
//	    break;
//	    default:
//		throw new IllegalArgumentException();
		}
	}
}
