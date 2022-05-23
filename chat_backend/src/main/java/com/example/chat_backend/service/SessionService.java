package com.example.chat_backend.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface SessionService {
    void initSession(String sessionId, WebSocketSession webSocketSession) throws IOException;
    void setSession(String userId, String sessionId);
    void deleteSession(String userId, String sessionId);
}
