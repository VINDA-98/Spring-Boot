package top.weidaboy.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import top.weidaboy.entity.Book;

@Service
public class Bookservice {

    //监听消息
//    @RabbitListener(queues = "firstQueue")
//    public void getReceive01(Book book){
//        System.out.println("收到新的信息:"+book);
//    }


    @RabbitListener(queues = "firstQueue")
    public void getReceive02(Message message){
        System.out.println("信息接收主要内容："+message.getBody());
        System.out.println("信息接收主要属性："+message.getMessageProperties());

    }
}
