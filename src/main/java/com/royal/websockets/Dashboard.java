package com.royal.websockets;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.royal.dao.DespesaUsuarioDAO;
import com.royal.model.DespesaUsuario;
import com.royal.Sistema;
import com.royal.dao.ReceitaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.ReceitaUsuario;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
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
	System.out.println(s + " <<<<<<");

	var pessoa = Sistema.PESSOAS.get(token);

	if (pessoa == null) {
	    s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Não posso permitir seu login"));
	} else {

	    if (!SESSOES.containsKey(token)) {
		var list = new ArrayList<Session>();
		list.add(s);
		SESSOES.put(token, list);
	    } else {
		SESSOES.get(token).add(s);
	    }
	}

    }

    @OnMessage
    public void mensagem(String mensagem, Session s, @PathParam("token") String token) throws IOException, SQLException {

	var json = JsonIterator.deserialize(mensagem);

	switch (json.get("metodo").mustBe(ValueType.STRING).asString()) {
	    case "despesa":
		DespesaData.tratador(json, s, token);
		break;
	    case "receita":
		ReceitaData.tratador(json, s, token);
		break;
	    default:
		throw new IllegalArgumentException();
	}
    }

    @OnClose
    public void fechar(Session s, @PathParam("token") String token) {
	System.out.println(s + " <<<<<<");

	var sessoes = SESSOES.get(token);

	if (sessoes != null) {
	    sessoes.remove(s);
	    System.out.println(sessoes);
	}
    }

    @OnError
    public void deuMerda(Session s, Throwable t, @PathParam("token") String token) throws IOException {
	final String message;

	t.printStackTrace();

	if (t instanceof com.jsoniter.spi.InvalidFieldException) {
	    message = "Faltou campos";
	} else if (t instanceof com.jsoniter.spi.TypeMismatchException) {
	    message = "Tipo incorreto";
	} else if (t instanceof JsonException) {
	    message = "JSON invalido";
	} else {
	    message = "Erro aleatório";
	}

	s.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, message));
    }
}

class DespesaData {

    private DespesaData() {
    }

    static void tratador(Any json, Session s, String token) throws SQLException {

	switch (json.get("arg").mustBe(ValueType.STRING).asString()) {
	    case "inserir": {
		var valor = BigDecimal.valueOf(json.get("valor").mustBe(ValueType.NUMBER).asDouble());
		var usuario = Sistema.PESSOAS.get(token).usuario;
		var pendente = json.get("pendente");

		DespesaUsuarioDAO.gravar(
			new DespesaUsuario.Builder(
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
	    }
	}
    }
}

class ReceitaData {

    private ReceitaData() {
    }

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
	    }
	}
    }
}
