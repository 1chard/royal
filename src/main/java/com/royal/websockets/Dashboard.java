package com.royal.websockets;

import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.qsoniter.spi.JsonException;
import com.royal.Sistema;
import com.royal.servlet.Erro;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author richard
 */
@ServerEndpoint("/dashboard/{token}")
public class Dashboard {

    public final static Map<String, List<Session>> SESSOES = Collections.synchronizedMap(new HashMap<>());

    public static void enviar(String token, String mensagem) {
        Dashboard.SESSOES.get(token).forEach(sessao -> {
            synchronized (sessao) {
                try {
                    sessao.getBasicRemote().sendText(mensagem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @OnOpen
    public void abrir(Session s, @PathParam("token") String token) throws IOException {
        var pessoa = Sistema.PESSOAS.get(token);

        System.out.println(token);

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

    @OnError
    public void deuMerda(Session s, Throwable t, @PathParam("token") String token) throws IOException {
        t.printStackTrace();

		fechar(s, token);

        Erro.enviador(t);
    }

    @OnClose
    public void fechar(Session s, @PathParam("token") String token) {
        var sessoes = SESSOES.get(token);

        if (sessoes != null) {
            sessoes.remove(s);
        }
    }

    @OnMessage
    public void mensagem(String mensagem, Session s, @PathParam("token") String token) throws IOException, SQLException {

        System.out.println(mensagem);

        var json = JsonIterator.deserialize(mensagem);

        switch (json.get("metodo").mustBe(ValueType.STRING).asString()) {
            case "despesa" -> DespesaTratador.tratador(json, s, token);
            case "receita" -> ReceitaTratador.tratador(json, s, token);
            default -> throw new IllegalArgumentException();
        }
    }
}
