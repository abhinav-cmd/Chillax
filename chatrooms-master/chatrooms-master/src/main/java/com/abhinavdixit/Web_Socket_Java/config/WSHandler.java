package com.abhinavdixit.Web_Socket_Java.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

@Component
public class WSHandler implements WebSocketHandler{
	Map<WebSocketSession,String> connectionsMap = new HashMap<>();

	@Override
	public void afterConnectionClosed(WebSocketSession sess, CloseStatus arg1) throws Exception {
		connectionsMap.remove(sess);
		System.out.println("connection removed : active connections: "+connectionsMap.size());
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession sess) throws Exception {
		connectionsMap.put(sess,"");
		System.out.println("connection added : active connections: "+connectionsMap.size());
		
	}

	@Override
	public void handleMessage(WebSocketSession sess, WebSocketMessage<?> message) throws Exception {
		System.out.println("received payload : "+message.getPayload().toString());
		Map value = new Gson().fromJson(message.getPayload().toString(), Map.class);
		if(value.get("name")!=null){
			connectionsMap.replace(sess, "", value.get("name").toString());
		}else{
			Arrays.asList(connectionsMap.keySet().toArray()).forEach((s)->{
				try {
					System.out.println("sending out by "+ connectionsMap.get(sess) +" to "+ connectionsMap.get(s));
					((WebSocketSession) s).sendMessage(new TextMessage(connectionsMap.get(sess) +" : "+ value.get("message")));
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			});
		}
		
	
		
		
		
	}

	@Override
	public void handleTransportError(WebSocketSession sess, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
