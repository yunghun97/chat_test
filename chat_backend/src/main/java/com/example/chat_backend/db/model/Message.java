package com.example.chat_backend.db.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message extends MessageType implements Serializable {
    private String sessionId;
    private String author;
    private String content;
    private String timestamp;
}
