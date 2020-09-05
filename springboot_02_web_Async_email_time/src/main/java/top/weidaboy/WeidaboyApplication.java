package top.weidaboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync        //开启异步注解功能
@EnableScheduling   //开启定时功能
public class WeidaboyApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeidaboyApplication.class, args);
    }

}
