package com.example.chat_backend.db.model;

import com.example.chat_backend.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@ToString
public class UserDto {
    private String id;
    private String server;
    private LocalDateTime loginTime;

    public UserDto(String id, String server, LocalDateTime loginTime) {
        this.id = id;
        this.server = server;
        this.loginTime = loginTime;
    }
}
