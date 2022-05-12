package com.example.chat_backend.db.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private String server;
    private String author;
    private String content;
    private String timestamp;
}
