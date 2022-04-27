package com.example.chat_backend.controller;

import com.example.chat_backend.constant.KafkaConstants;
import com.example.chat_backend.db.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequestMapping("/kafka")
public class ChatController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping("/publish")
    public void sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now().toString());
        try{
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message){
        return  message;
    }
}
