package top.weidaboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import top.weidaboy.util.interceptor.LoginInterceptor;

@SpringBootApplication
public class SpringbootLayuiApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLayuiApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/static/**", "/register", "/login/**", "/**.html", "/**.ico", "/auths/login"
        );

    }
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/favicon.ico")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
