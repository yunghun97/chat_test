package com.example.chat_backend.controller;

import com.example.chat_backend.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("set")
    public ResponseEntity setUser(@RequestBody Map<String, String> map){
        sessionService.setSession(map.get("author"),map.get("sessionId"));
        return new ResponseEntity(HttpStatus.OK);
    }

}
