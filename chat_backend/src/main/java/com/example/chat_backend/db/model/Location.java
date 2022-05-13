package com.example.chat_backend.db.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    private String x;
    private String y;
    private String z;
    private String locationX;
    private String locationY;
    private String locationZ;
}
