package com.example.chat_backend.service;

import com.example.chat_backend.constant.KafkaConstants;
import com.example.chat_backend.db.model.Message;
import com.example.chat_backend.db.model.UserState;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

@Service
public class ChatService {

    private final String broker1 = "k6b102.p.ssafy.io:9091";
    private final String broker2 = "k6b102.p.ssafy.io:9092";
    private final String broker3 = "k6b102.p.ssafy.io:9093";

    private final String chatTopic = "chat";
    private final String userTopic = "user";
//    private HashMap<String, Boolean> topicMap;
    private Producer<String, Message> messageProducer;
    private Producer<String, UserState> userMessageProducer;

    public void sendMessage(Map<String, String> map){

        Message message = new Message();
        message.setSessionId(map.get("sessionId"));
        message.setAuthor(map.get("author"));
        message.setContent(map.get("content"));
        message.setType("message");
        message.setTimestamp(LocalDateTime.now().toString());
        // type = message 는 메시지

//        if(!findTopic(server)){ // 토픽이 없는 경우
//            createTopic(server);
//        }

        try {
            ProducerRecord<String, Message> record = new ProducerRecord<>(chatTopic, message); //ProducerRecord 오브젝트를 생성합니다. (Topic,Key,Value) (Topic,Value)
            RecordMetadata metadata = messageProducer.send(record).get(); //get() 메소드를 이용해 카프카의 응답을 기다립니다. 메시지가 성공적으로 전송되지 않으면 예외가 발생하고, 에러가 없다면 RecordMetadata를 얻게 됩니다.
            System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Received Message: %s\n", metadata.topic(), metadata.partition()
                    , metadata.offset(), record.key(), record.value());
        } catch (Exception e){
            e.printStackTrace(); //카프카로 메시지를 보내기 전과 보내는 동안 에러가 발생하면 예외가 발생합니다.
        }
    }

    // Kafka에 user Topic ~님이 입장, 퇴장 알림을 위한 메시지 보내기
    public void sendUserStateMessage(String author, boolean state){
        UserState userState = new UserState();
        if(state){
            userState.setType("join");
        }else{
            userState.setType("leave");
        }
        userState.setState(state);
        userState.setAuthor(author);
        try {
            ProducerRecord<String, UserState> record = new ProducerRecord<>(userTopic, userState); //ProducerRecord 오브젝트를 생성합니다. (Topic,Key,Value) (Topic,Value)
            RecordMetadata metadata = userMessageProducer.send(record).get(); //get() 메소드를 이용해 카프카의 응답을 기다립니다. 메시지가 성공적으로 전송되지 않으면 예외가 발생하고, 에러가 없다면 RecordMetadata를 얻게 됩니다.
            System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Received Message: %s\n", metadata.topic(), metadata.partition()
                    , metadata.offset(), record.key(), record.value());
        } catch (Exception e){
            e.printStackTrace(); //카프카로 메시지를 보내기 전과 보내는 동안 에러가 발생하면 예외가 발생합니다.
        }
    }

    // Producer 만들기 메시지 전달
    @PostConstruct
    public void setMessageProducer(){
        Properties props = new Properties(); //Properties 오브젝트를 시작합니다.
        props.put("bootstrap.servers", KafkaConstants.KAFKA_BROKER); //브로커 리스트를 정의합니다.
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"); //메시지 키와 벨류에 문자열을 지정하므로 내장된 StringSerializer를 지정합니다.
        props.put("value.serializer",
                "org.springframework.kafka.support.serializer.JsonSerializer");
        messageProducer = new KafkaProducer<>(props);  //Properties 오브젝트를 전달해 새 프로듀서를 생성합니다.
    }
    // 사용자 입장 퇴장 전달 용
    @PostConstruct
    public void setUserMessageProducer(){
        Properties props = new Properties(); //Properties 오브젝트를 시작합니다.
        props.put("bootstrap.servers", KafkaConstants.KAFKA_BROKER); //브로커 리스트를 정의합니다.
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"); //메시지 키와 벨류에 문자열을 지정하므로 내장된 StringSerializer를 지정합니다.
        props.put("value.serializer",
                "org.springframework.kafka.support.serializer.JsonSerializer");
        userMessageProducer = new KafkaProducer<>(props);  //Properties 오브젝트를 전달해 새 프로듀서를 생성합니다.
    }
}
