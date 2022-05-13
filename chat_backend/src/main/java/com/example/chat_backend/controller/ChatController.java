package com.example.chat_backend.controller;

import com.example.chat_backend.db.model.Message;
import com.example.chat_backend.db.model.UserDto;
import com.example.chat_backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/publish")
    public void sendMessage(@RequestBody Map<String, String> map){
        chatService.sendMessage(map);
    }
}
