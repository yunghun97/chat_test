package com.example.chat_backend.service;

import com.example.chat_backend.db.model.Message;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
@Service
public class ChatService {

    private final String broker1 = "k6b102.p.ssafy.io:9091";
    private final String broker2 = "k6b102.p.ssafy.io:9092";
    private final String broker3 = "k6b102.p.ssafy.io:9093";

    private String defaultTopic = "chat-test";
//    private HashMap<String, Boolean> topicMap;
    private Producer<String, Message> messageProducer;

    public void sendMessage(Map<String, String> map){

        String server = map.get("server");
        Message message = new Message();
        message.setAuthor(map.get("author"));
        message.setContent(map.get("content"));
        message.setTimestamp(LocalDateTime.now().toString());



//        if(!findTopic(server)){ // 토픽이 없는 경우
//            createTopic(server);
//        }

        try {
            ProducerRecord<String, Message> record = new ProducerRecord<>(defaultTopic, server, message); //ProducerRecord 오브젝트를 생성합니다. (Topic,Key,Value) (Topic,Value)
            RecordMetadata metadata = messageProducer.send(record).get(); //get() 메소드를 이용해 카프카의 응답을 기다립니다. 메시지가 성공적으로 전송되지 않으면 예외가 발생하고, 에러가 없다면 RecordMetadata를 얻게 됩니다.
            System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Received Message: %s\n", metadata.topic(), metadata.partition()
                    , metadata.offset(), record.key(), record.value());
        } catch (Exception e){
            e.printStackTrace(); //카프카로 메시지를 보내기 전과 보내는 동안 에러가 발생하면 예외가 발생합니다.
        }
    }
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/group")
//    public Message broadcastGroupMessage(@Payload Message message){
//        return  message;
//    }

    /* Topic 나누어서 할 때
    public boolean findTopic(String topicName){
        return topicMap.containsKey(topicName);
    }

    public void createTopic(String server){
        List<NewTopic> newTopics = new ArrayList<>();
        int partition = 3;
        short replication = 3;
        Properties properties = new Properties();
        properties.put("bootstrap.servers", broker1+","+broker2+","+broker3);
        Admin admin = Admin.create(properties);
        NewTopic topic = new NewTopic(server, partition, replication);
        newTopics.add(topic);
        admin.createTopics(newTopics);
        try{
            admin.createTopics(newTopics);
            Set<String> topicNames = admin.listTopics().names().get();
            while(true){
                for(String topicName : topicNames) {
                    if(topicName.equals(server)){
                        topicMap.put(server,true);
                        return;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 처음 토픽 목록 가져오기
    @PostConstruct
    public void getAllTopic() throws ExecutionException, InterruptedException {
        System.out.println("진입");
        topicMap = new HashMap<>();
        Properties properties = new Properties();
        properties.put("bootstrap.servers", broker1);
        Admin admin = Admin.create(properties);
        Set<String> topicNames = admin.listTopics().names().get();
        for(String topicName : topicNames){
            topicMap.put(topicName,true);
        }
        System.out.println(topicMap.toString());
        admin.close();
    }
    */

    // Producer 만들기
    @PostConstruct
    public void setMessageProducer(){
        Properties props = new Properties(); //Properties 오브젝트를 시작합니다.
        props.put("bootstrap.servers", broker1+","+broker2+","+broker3); //브로커 리스트를 정의합니다.
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"); //메시지 키와 벨류에 문자열을 지정하므로 내장된 StringSerializer를 지정합니다.
        props.put("value.serializer",
                "org.springframework.kafka.support.serializer.JsonSerializer");
        messageProducer = new KafkaProducer<>(props);  //Properties 오브젝트를 전달해 새 프로듀서를 생성합니다.
    }

}
