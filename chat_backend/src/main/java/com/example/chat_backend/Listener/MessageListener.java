package com.example.chat_backend.Listener;

import com.example.chat_backend.constant.KafkaConstants;
import com.example.chat_backend.db.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(ConsumerRecord<String, Message> record){
        System.out.println(record.key());
        System.out.println(record.value().toString()+" 이벤트 발생");
        template.convertAndSend("/topic/"+record.key(), record.value());

    }

    public void changeUserList(){

    }


}
/*
@KafkaListener 어노테이션을 통해 Kafka로부터 메시지를 받을 수 있음

template.convertAndSend를 통해 WebSocket으로 메시지를 전송

Message를 작성할 때 경로 잘 보고 import하시길...
 */