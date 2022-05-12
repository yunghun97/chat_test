package com.example.chat_backend.controller;

import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.db.model.UserDto;
import com.example.chat_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RestController
@RequestMapping("/user")
public class UserController {
    HttpHeaders headers;

    @Autowired
    UserService userService;

    @PostMapping("/enter")
    public ResponseEntity<List<User>> enterUser(@RequestBody Map<String, String> map){
        UserDto userDto = new UserDto(map.get("id"), map.get("server"), LocalDateTime.now());
        headers = new HttpHeaders();
        headers.set("type", "participant");
        return new ResponseEntity<>(userService.enterUser(userDto),headers,HttpStatus.OK);
    }

    @PostMapping("/leave")
    public ResponseEntity<List<User>> leaveUser(@RequestBody Map<String, String> map) {
        UserDto userDto = new UserDto(map.get("id"), map.get("server"), LocalDateTime.now());
        headers = new HttpHeaders();
        headers.set("type", "participant");
        return new ResponseEntity<>(userService.leaveUser(userDto),headers,HttpStatus.OK);
    }

    @GetMapping("/connect")
    public void test(){
        System.out.println("사람이 입장하였습니다.");
    }
    @GetMapping("/disconnect")
    public void outTest(){
        System.out.println("사람이 나갔습니다.");
    }
}
