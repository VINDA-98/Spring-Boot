package top.weidaboy.provider.service.Impl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import top.weidaboy.provider.service.ITicketService;

//zookeeper:服务注册与发现
@Service      //使用后被注入,项目已启动就自动注册到注册中心
@Component    //放到spring容器中去
//新版:import org.apache.dubbo.config.annotation.DubboService;
//@DubboService //将服务发布出去
public class TicketServiceImpl implements ITicketService {

    @Override
    public String saleTicket(String name) {
        return "确认买票: "+name;
    }
}
