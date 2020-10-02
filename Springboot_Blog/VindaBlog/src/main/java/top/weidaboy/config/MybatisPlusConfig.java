package top.weidaboy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *通过@mapperScan注解指定要变成实现类的接口所在的包，
 * 然后包下面的所有接口在编译之后都会生成相应的实现类。
 * PaginationInterceptor是一个分页插件。
 */
@Configuration //说明是一个配置类
@EnableTransactionManagement //启注解事务管理支持，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan("top.weidaboy.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return  paginationInterceptor;
    }

}
