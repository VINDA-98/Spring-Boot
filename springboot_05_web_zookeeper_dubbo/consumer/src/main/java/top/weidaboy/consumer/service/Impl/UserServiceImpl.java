package top.weidaboy.consumer.service.Impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import top.weidaboy.consumer.service.IUserService;
import top.weidaboy.provider.service.ITicketService;

@Service//注入到容器中, 这个import org.springframework.stereotype.Service;
public class UserServiceImpl implements IUserService {

    @Reference //远程引用指定的服务,该注解会按照全类名来进行匹配,看是哪个给注册中心注册了这个全类名
    ITicketService ticketService;  //相同的接口名

    @Override
    public String buyTicket(String name) {
        return ticketService.saleTicket(name);
    }
}
