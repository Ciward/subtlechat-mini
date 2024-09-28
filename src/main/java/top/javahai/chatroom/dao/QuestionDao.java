package top.javahai.chatroom.dao;

import top.javahai.chatroom.entity.Question;

import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;

public interface QuestionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Question queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Question> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param Question 实例对象
     * @return 对象列表
     */
    List<Question> queryAll(Question Question);

    /**
     * 新增数据
     *
     * @param Question 实例对象
     * @return 影响行数
     */
    int insert(Question Question);

    /**
     * 修改数据
     *
     * @param Question 实例对象
     * @return 影响行数
     */
    int update(Question Question);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
