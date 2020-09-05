package top.weidaboy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.weidaboy.consumer.service.IUserService;

import javax.annotation.Resource;

@SpringBootTest
class WeidaboyApplicationTests {

    @Autowired
    IUserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.buyTicket("唐人街探案三"));
    }
}
