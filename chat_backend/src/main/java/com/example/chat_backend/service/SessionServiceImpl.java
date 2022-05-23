package com.example.chat_backend.service;

import com.example.chat_backend.constant.SessionConstants;
import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.handler.ChatHandler;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final SessionConstants sessionConstants;

    private final ChatService chatService;

    private final UserService userService;

    @Override
    public void initSession(String sessionId, WebSocketSession webSocketSession) throws IOException {
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("type","connect");
        hMap.put("sessionId",sessionId);
        Gson gson = new Gson();
        String json = gson.toJson(hMap);
        byte[] byteArr = json.getBytes();
        webSocketSession.sendMessage(new TextMessage(byteArr)); // 해당 소켓에 메시지 전달
    }

    @Override
    public void setSession(String author, String sessionId) { // 참가자 등록 -> 이후 모든 소켓에 UserInfo 전달
        sessionConstants.getSessionId_authorMap().put(sessionId,author);
        sessionConstants.getAuthor_sessionIdMap().put(author,sessionId);
        // kafka에 메시지 보내고 UserList return
        userService.enterUser(author, sessionId);
        chatService.sendUserStateMessage(author, true);
    }

    @Override
    public void deleteSession(String author, String sessionId) { // 참가자 삭제 -> 이후 모든 소켓에 UserInfo 전달
        userService.leaveUser(author);
        sessionConstants.getSessionId_authorMap().remove(sessionId);
        sessionConstants.getAuthor_sessionIdMap().remove(author);
        chatService.sendUserStateMessage(author, false);
    }
}
