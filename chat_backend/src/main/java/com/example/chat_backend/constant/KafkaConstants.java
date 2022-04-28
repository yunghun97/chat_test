package com.example.chat_backend.constant;

public class KafkaConstants {
    public static final String KAFKA_TOPIC = "chat-test"; // kafka 토픽 이름
    public static final String GROUP_ID = "foo"; // consumer를 식별할 수 있는 그룹
    public static final String KAFKA_BROKER = "localhost:9091"; // kafka 클러스토 호스트:포트
//    public static final String KAFKA_BROKER = "k6b102.p.ssafy.io:9091"; // kafka 클러스토 호스트:포트
}
