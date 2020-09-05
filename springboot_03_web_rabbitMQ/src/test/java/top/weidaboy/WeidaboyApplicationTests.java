package top.weidaboy;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.weidaboy.entity.Book;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class WeidaboyApplicationTests {
    //添加RabbitMQ模板对象
    @Autowired
    private  RabbitTemplate rabbitTemplate;

    //添加Amqp管理员，用于创建路由交换器，创建绑定规则
    @Autowired
    private AmqpAdmin amqpAdmin;

    /*创建交换机、队列信息和绑定规则*/
    @Test
    public void createExchange(){
        //declare:声明，声明新的路由器
        amqpAdmin.declareExchange(new DirectExchange("test.Exchange"));
        //declare:声明，声明新的队列消息
        amqpAdmin.declareQueue(new Queue("test.Queue",true));
        //declare:声明，声明新的绑定规则
        /**
         String destination : 需要绑定的队列信息
         Binding.DestinationType destinationType ： 绑定队列的类型
         String exchange    ：需要绑定的交换器
         String routingKey  ：发送的路由键
         *
         */
        amqpAdmin.declareBinding(new Binding("test.Queue", Binding.DestinationType.QUEUE,
                "test.Exchange","test",null));
    }

    @Test
    void contextLoads() {
        //两种发送方式：
        //Message需要自己构造一个;定义消息体内容和消息头
        //rabbitTemplate.send(exchage,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
        //rabbitTemplate.convertAndSend(exchage,routeKey,object);

    }

    @Test //单播
    void test01(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个书籍信息");
        //添加数据，字符型、整型、boolean类型
        map.put("data", Arrays.asList("helloVinda!",666,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("firstExchange","first"
                ,new Book("测试三","Vinda"));
    }

    @Test //接收信息，接收后queue里面的值会被清空
    void test02(){
        //对应的队列名称要写正确
        Object obj = rabbitTemplate.receiveAndConvert("firstQueue");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }

    @Test //测试监听
    void test03(){
        rabbitTemplate.convertAndSend("firstExchange","first"
                ,new Book("测试监听","Vinda"));
    }
}
