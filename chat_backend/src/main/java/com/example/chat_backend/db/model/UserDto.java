package com.example.chat_backend.db.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserDto {
    private String id;
    private LocalDateTime loginTime;
}
