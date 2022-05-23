package com.example.chat_backend.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Getter
@Setter
public class SessionConstants {
    private HashMap<String, String> author_sessionIdMap = new HashMap<>();
    private HashMap<String, String> sessionId_authorMap = new HashMap<>(); // 세션 이름, 유저 ID 저장
}
