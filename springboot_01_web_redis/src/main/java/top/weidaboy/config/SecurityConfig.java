//package top.weidaboy.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
///*不要默认登录拦截，springboot 自动配置了拦截*/
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //super.configure(http);
//        //配置不需要登陆验证
//        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
//    }
//}
