package com.example.chat_backend.controller;

import com.example.chat_backend.db.model.Message;
import com.example.chat_backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequestMapping("/kafka")
public class ChatController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    ChatService chatService;

    @PostMapping("/publish")
    public void sendMessage(@RequestBody Map<String, String> map){
        chatService.sendMessage(map);

//        try{
//            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
//        }catch (Exception e){
//            throw  new RuntimeException(e);
//        }


    }
}
