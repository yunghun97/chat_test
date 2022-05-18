package com.example.chat_backend.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {
    private final ChatHandler chatHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "my-chat").setAllowedOrigins("*");

    }
}


/*
    endpoint에 작성한 값으로 client측에서 연결 (추후 React를 이용한 프론트단 작업 예정)

    setAllowedOrigins는 허용할 주소를 작성하면 되는데, "*"값은 모두 허용한다는 뜻

    applicationDestinationPrefixes는 메시지를 전송할 때 사용하는 url
*/