package com.example.chat_backend.db.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserState extends MessageType{
    private String author;
    private boolean state; // true면 참가 false면 퇴장
}
