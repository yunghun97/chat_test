package com.example.chat_backend.Listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    // 소켓 연결
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        System.out.println(event.toString());
    }

    // 소켓 연결 X
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        System.out.println(event.toString());
    }
}
