package com.example.chat_backend.db.repository;

import com.example.chat_backend.db.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRedisRepository extends CrudRepository<User, String> {
    List<User> findAllByServer(String server);
}
