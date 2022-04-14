package com.royal.websockets;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.royal.servlet.Sistema;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author richard
 */
@ServerEndpoint("/data/{token}")
public class Data {
    public final static Map<String, Session> SESSOES = Collections.synchronizedMap(new HashMap<>());
    
	@OnOpen
	public void abrir(Session s, @PathParam("token") String token) throws IOException {
	    var pessoa = Sistema.PESSOAS.get(token);
	    
	    if(pessoa == null){
		s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Não posso permitir seu login"));
	    }
	    
	    SESSOES.put(token, s);
	}
	
	@OnMessage
	public void mensagem(String mensagem, Session s){
	    var json = JsonIterator.deserialize(mensagem);
	    
	    switch (json.get("metodo").toString()) {
		case "despesa":
		    DespesaData.tratador(json, s);

		break;
		default:
		    throw new IllegalArgumentException();
	    }
	}
	
	@OnClose
	public void fechar(Session s){
		
	}
}

class DespesaData{
    private DespesaData(){}
    
    static void tratador(Any json, Session s){
	
    }
}
