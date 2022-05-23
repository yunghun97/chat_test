package com.example.chat_backend.service;

import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.db.model.UserDto;

import java.util.List;

public interface UserService {
    List<User> enterUser(String author, String sessionId);
    List<User> leaveUser(String author);
    List<User> findUserByServer();
}
