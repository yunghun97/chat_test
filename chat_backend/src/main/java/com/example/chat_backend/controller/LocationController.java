package com.example.chat_backend.controller;

import com.example.chat_backend.db.model.Location;
import com.example.chat_backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RequestMapping("/kafka")
@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    public ResponseEntity<Location> enterUser(@RequestBody Map<String, String> map){
        return new ResponseEntity<>(, HttpStatus.OK);
    }
}
