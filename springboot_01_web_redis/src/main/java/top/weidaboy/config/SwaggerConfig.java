package top.weidaboy.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket docket(){
        //Reques tHandlerSelectors,配置要扫描接口的方式
        //basePackage:指定要扫描的包
        //any():扫描全部
        //none():不扫描
        //withClassAnnotation:扫描类上的注解
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("达哥")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.weidaboy.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Vinda 学生信息管理模块",
                "夜空中最亮的星",
                "1.0",
                "",
                new Contact(
                "Vinda",
                "weidaboy.top/newlogin.jsp",
                "760319460@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }


}
