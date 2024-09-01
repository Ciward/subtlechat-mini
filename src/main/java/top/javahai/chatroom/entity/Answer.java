package top.javahai.chatroom.entity;

import lombok.Data;

@Data
public class Answer {
    private Integer id;
    private String content;
    private Integer questionId;
}
