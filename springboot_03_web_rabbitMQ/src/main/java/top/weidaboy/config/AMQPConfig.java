package top.weidaboy.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;


@Configuration  //说明这是一个配置类，类中有我们自定以的配置类方法
public class AMQPConfig {

        @Bean //自动注入消息转换器 converter：转换器
        public MessageConverter getMessageConverter(){
            return new Jackson2JsonMessageConverter();

        }

}
