package top.javahai.chatroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
@MapperScan("top.javahai.chatroom.dao")
public class ChatroomApplication {
  public static void main(String[] args) {
    SpringApplication.run(ChatroomApplication.class, args);
  }
}
