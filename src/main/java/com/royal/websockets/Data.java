package com.royal.websockets;

import com.jsoniter.JsonIterator;
import com.royal.servlet.Sistema;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *
 * @author richard
 */
@ServerEndpoint("/data/{token}")
public class Data {
	@OnOpen
	public void abrir(Session s, @PathParam("token") String token) throws IOException {
	    var pessoa = Sistema.SESSOES.get(token);
	    
	    System.out.println("teste");
	    
	    if(pessoa == null){
		
	    System.out.println("asdf");
		s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Não posso permitir seu login"));
	    }
	}
	
	@OnMessage
	public void mensagem(String mensagem, Session s){
	    var json = JsonIterator.deserialize(mensagem);
	    
	    
	}
	
	@OnClose
	public void fechar(Session s){
		
	}
}
