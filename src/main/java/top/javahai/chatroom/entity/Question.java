package top.javahai.chatroom.entity;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String content;
    private Integer userId;
}
