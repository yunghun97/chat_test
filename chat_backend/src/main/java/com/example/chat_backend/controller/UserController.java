package com.example.chat_backend.controller;

import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.db.model.UserDto;
import com.example.chat_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/enter")
    public List<User> enterUser(@RequestBody Map<String, String> map){
        UserDto userDto = new UserDto(map.get("id"), map.get("server"), LocalDateTime.now());
        return userService.enterUser(userDto);
    }

    @PostMapping("/leave")
    public List<User> leaveUser(@RequestBody Map<String, String> map) {
        UserDto userDto = new UserDto(map.get("id"), map.get("server"), LocalDateTime.now());
        return userService.leaveUser(userDto);
    }
}
