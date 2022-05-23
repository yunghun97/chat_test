package com.example.chat_backend.handler;

import com.example.chat_backend.constant.SessionConstants;
import com.example.chat_backend.service.SessionService;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@RequiredArgsConstructor
@Component
@Getter
public class ChatHandler extends TextWebSocketHandler{

    private final SessionService sessionService;

    private final SessionConstants sessionConstants;

    private HashMap<String, WebSocketSession> sessionId_sessionMap = new HashMap<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println(payload);
//        for(WebSocketSession sess: list) {
//            sess.sendMessage(message);
//        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 세션 접속
        sessionId_sessionMap.put(session.getId(),session);
        System.out.println(session + " 클라이언트 접속"+session.getId());

        // 해당소켓에 메시지 전달
        sessionService.initSession(session.getId(),session);


    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session + " 클라이언트 접속 해제");
        System.out.println(sessionConstants.getSessionId_authorMap().toString());
        sessionId_sessionMap.remove(session.getId());
        sessionService.deleteSession(sessionConstants.getSessionId_authorMap().get(session.getId()), session.getId());
    }
}
