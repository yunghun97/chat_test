package com.example.chat_backend.constant;

import lombok.Getter;

@Getter
public class KafkaConstants {
    public static final String KAFKA_TOPIC_CHAT = "chat"; // kafka 토픽 이름
    public static final String GROUP_ID_CHAT = "foo_chat"; // consumer를 식별할 수 있는 그룹

    public static final String KAFKA_TOPIC_USER = "user"; // kafka 토픽 이름
    public static final String GROUP_ID_USER = "foo_user"; // consumer를 식별할 수 있는 그룹
//    public static final String KAFKA_BROKER = "localhost:9091"; // kafka 클러스토 호스트:포트
    public static final String KAFKA_BROKER = "k6b102.p.ssafy.io:9091,k6b102.p.ssafy.io:9092,k6b102.p.ssafy.io:9093,k6b102.p.ssafy.io:9094,k6b102.p.ssafy.io:9095,k6b102.p.ssafy.io:9096"; // kafka 클러스토 호스트:포트
}
