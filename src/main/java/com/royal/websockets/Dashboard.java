package com.royal.websockets;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.royal.dao.DespesaUsuarioDAO;
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
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.Data;

/**
 *
 * @author richard
 */
@ServerEndpoint("/dashboard/{token}")
public class Dashboard {

    public final static Map<String, List<Session>> SESSOES = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void abrir(Session s, @PathParam("token") String token) throws IOException {
	var pessoa = Sistema.PESSOAS.get(token);

	if (pessoa == null) {
	    s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Não posso permitir seu login"));
	}

	if (!SESSOES.containsKey(token)) {
	    var list = new ArrayList<Session>();
	    list.add(s);
	    SESSOES.put(token, list);
	} else {
	    SESSOES.get(token).add(s);
	}

    }

    @OnMessage
    public void mensagem(String mensagem, Session s) throws IOException, SQLException {

	var json = JsonIterator.deserialize(mensagem);

	switch (json.get("metodo").asString()) {
	    case "despesa":
		DespesaData.tratador(json, s);
		break;
	    default:
		throw new IllegalArgumentException();
	}

	s.getBasicRemote().sendText("comi o cu de quem ta lendo");
    }

    @OnClose
    public void fechar(Session s) {

    }
}

class DespesaData {

    private DespesaData() {
    }

    static void tratador(Any json, Session s) throws SQLException {

	switch (json.asString("arg")) {
	    case "inserir": {
		var token = s.getPathParameters().get("token");
		var valor = json.get("valor").asDouble();

		DespesaUsuarioDAO.gravar(
			new DespesaUsuario.Builder(
				BigDecimal.valueOf(valor),
				Date.valueOf(json.get("data").asString()),
				json.get("pendente").asBoolean(),
				json.get("descricao").asString(),
				json.get("favorito").asBoolean(),
				Sistema.PESSOAS.get(token).usuario.id,
				json.get("idcategoria").asInt()
			).build()
		);

		Dashboard.SESSOES.get(token).forEach(sessao -> {
		    synchronized (sessao) {
			try {
			    sessao.getBasicRemote().sendText(JsonStream.serialize(Map.of("metodo", "despesa", "arg", "adicionar", "valor", valor)));
			} catch (IOException ex) {
			    throw new RuntimeException(ex);
			}
		    }
		});
	    }
	}
    }
}
