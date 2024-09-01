package top.javahai.chatroom.dao;

import top.javahai.chatroom.entity.Message;

import org.apache.ibatis.annotations.Param;

public interface QuestionDao {

    /**
     * 插入一条新的问题记录
     *
     * @param question 实例对象
     * @return 影响行数
     */
    int insert(Message question);

    // 其他你需要的操作方法
}
