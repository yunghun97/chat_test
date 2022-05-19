package com.example.chat_backend.Listener;

import com.example.chat_backend.config.ChatHandler;
import com.example.chat_backend.constant.KafkaConstants;
import com.example.chat_backend.db.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Component
@RequiredArgsConstructor
public class MessageListener {

//    SimpMessagingTemplate template;

    private final ChatHandler chatHandler;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) throws IOException {
        HashMap<String, String> hMap = new HashMap<>();
        hMap.put("author",message.getAuthor());
        hMap.put("content",message.getContent());
        hMap.put("timestamp",message.getTimestamp());
        System.out.println(hMap.toString()+"메시지 수신");
        byte[] byteArr = hMap.toString().getBytes();
        TextMessage textMessage = new TextMessage(byteArr);
        for(Entry<String, WebSocketSession> entry : chatHandler.getSessionMap().entrySet()) {
            entry.getValue().sendMessage(textMessage);
        }
//        template.convertAndSend("/topic/"+message.getServer(), message);

    }

}
/*
@KafkaListener 어노테이션을 통해 Kafka로부터 메시지를 받을 수 있음

template.convertAndSend를 통해 WebSocket으로 메시지를 전송

Message를 작성할 때 경로 잘 보고 import하시길...
 */