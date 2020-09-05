package top.weidaboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication()
@EnableCaching //开启缓存机制
public class WeidaboyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeidaboyApplication.class, args);
    }

}
