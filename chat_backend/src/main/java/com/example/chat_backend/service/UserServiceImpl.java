package com.example.chat_backend.service;

import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.db.repository.UserRedisRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRedisRepository userRedisRepository;
    
    private final RedisTemplate<String, String> redisTemplate;

    private final String user_key = "userList";

    @Override
    public List<User> enterUser(String author, String sessionId){
        System.out.println("redis 값 넣기"+author+" "+sessionId);
        redisTemplate.opsForHash()
                .put(
                        user_key,
                        author,
                        sessionId
                );

        return findUserByServer();
    }

    @Override
    public List<User> leaveUser(String author) {
        System.out.println(user_key+" "+author);
        redisTemplate.opsForHash()
                .delete(
                        user_key,
                        author
                );
        return findUserByServer();
    }

    public List<User> findUserByServer(){
        List<User> list = new ArrayList<>();
        for(Map.Entry<Object, Object> entry : redisTemplate.opsForHash().entries(user_key).entrySet()){
            User user = new User(entry.getKey().toString(),entry.getValue().toString());
            list.add(user);
        }
        return list;
    }
}
