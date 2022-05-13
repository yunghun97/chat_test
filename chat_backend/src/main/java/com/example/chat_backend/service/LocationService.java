package com.example.chat_backend.service;

import com.example.chat_backend.db.model.Location;

public interface LocationService {
    void sendLocation(Location location);
}
