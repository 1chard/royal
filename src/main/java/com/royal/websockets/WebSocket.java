package com.royal.websockets;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 *
 * @author richard
 */
@ServerEndpoint("/")
public class WebSocket {
	@OnOpen
	public void abrir(Session s) {
		System.out.println("abri");
	}
	
	@OnMessage
	public void mensagem(String mensagem, Session s){
	}
	
	@OnClose
	public void fechar(Session s){
		
	}
}
