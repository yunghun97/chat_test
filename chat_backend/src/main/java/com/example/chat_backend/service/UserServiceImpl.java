package com.example.chat_backend.service;

import com.example.chat_backend.db.entity.User;
import com.example.chat_backend.db.model.UserDto;
import com.example.chat_backend.db.repository.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRedisRepository userRedisRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<User> enterUser(UserDto userDto){
        redisTemplate.opsForHash()
                .put(
                        userDto.getServer(),
                        userDto.getId(),
                        userDto.getLoginTime().toString()
                );

        return findUserByServer(userDto);
    }

    @Override
    public List<User> leaveUser(UserDto userDto) {
        redisTemplate.opsForHash()
                .delete(
                        userDto.getServer(),
                        userDto.getId()
                );
        return findUserByServer(userDto);
    }

    private List<User> findUserByServer(UserDto userDto){

        List<User> list = new ArrayList<>();
        for(Map.Entry<Object, Object> entry : redisTemplate.opsForHash().entries(userDto.getServer()).entrySet()){
            list.add(new User(userDto.getServer(), (String) entry.getKey(), (String) entry.getValue()));
        }
        return list;
    }
}
