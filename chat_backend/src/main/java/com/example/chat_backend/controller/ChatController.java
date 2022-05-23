package com.example.chat_backend.controller;

import com.example.chat_backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
