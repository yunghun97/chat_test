package com.example.chat_backend.Listener;

import com.example.chat_backend.db.model.UserState;
import com.example.chat_backend.handler.ChatHandler;
import com.example.chat_backend.constant.KafkaConstants;
import com.example.chat_backend.db.model.Message;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

@Component
@RequiredArgsConstructor
public class MessageListener {

//    SimpMessagingTemplate template;

    private final ChatHandler chatHandler;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC_CHAT,
            groupId = KafkaConstants.GROUP_ID_CHAT
    )
    public void messageListen(Message message) throws IOException {
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("author",message.getAuthor());
        hMap.put("content",message.getContent());
        hMap.put("timestamp",message.getTimestamp());
        hMap.put("sessionId",message.getSessionId());
        hMap.put("type",message.getType());
        Gson gson = new Gson();
        String json = gson.toJson(hMap);
        System.out.println(json+"메시지 수신");
        byte[] byteArr = json.getBytes();
        TextMessage textMessage = new TextMessage(byteArr);
        if(message.getSessionId().equals("all")) { // 전체 메시지
            for (Entry<String, WebSocketSession> entry : chatHandler.getSessionId_sessionMap().entrySet()) {
                entry.getValue().sendMessage(textMessage);
            }
        }else{ // 귓속말
            chatHandler.getSessionId_sessionMap().get(message.getSessionId()).sendMessage(textMessage);
        }

    }
    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC_USER,
            groupId = KafkaConstants.GROUP_ID_USER
    )
    public void userStateListen(UserState userState) throws  IOException{
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("author",userState.getAuthor());
        hMap.put("state",userState.isState());
        hMap.put("type",userState.getType());
        Gson gson = new Gson();
        String json = gson.toJson(hMap);
        System.out.println(json+"회원 관련 수신");

        byte[] byteArr = json.getBytes();
        TextMessage textMessage = new TextMessage(byteArr);
        for (Entry<String, WebSocketSession> entry : chatHandler.getSessionId_sessionMap().entrySet()) {
            entry.getValue().sendMessage(textMessage);
        }
    }

}
/*
@KafkaListener 어노테이션을 통해 Kafka로부터 메시지를 받을 수 있음

template.convertAndSend를 통해 WebSocket으로 메시지를 전송

Message를 작성할 때 경로 잘 보고 import하시길...
 */