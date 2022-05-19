FROM openjdk:11
ARG JAR_FILE=./build/libs/chat_backend-0.0.1-SNAPSHOT.jar
copy ${JAR_FILE} chat.jar
ENTRYPOINT ["java", "-jar","chat.jar"]
EXPOSE 9998