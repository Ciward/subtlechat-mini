package top.javahai.chatroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.dao.NoticeDao;
import top.javahai.chatroom.entity.Notice;
import top.javahai.chatroom.service.NoticeService;
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {
}
