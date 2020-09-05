package top.weidaboy.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean  用户对象 第三步
     * @param securityManager  当前对象用户
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean Sbean = new ShiroFilterFactoryBean();
        //关联securityManager,设置安全管理器
        Sbean.setSecurityManager(securityManager);

        /**
         添加shiro的内置过滤器
         * anon:.无需认 证就可以访间
         * authc:必须认证了才能 让问
         * user:必须拥有记住我功能才能用
         * perms:拥有对某个资源的权限才能访间;
         * role: 拥有某个角色权限才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();

        //两个页面需要认证才可以访问
//        filterMap.put("/student/addStudent","authc");
//        filterMap.put("/student/updateStudent","anon");
//        filterMap.put("/student/deleteStudent","authc");

        //通配符
        //filterMap.put("/student/*","anon");

        //授权,这里第一个参数就是需要访问的页面，第二个就是权限名称
        //当然可以直接用上面的也行，这里是自定义权限访问名称
        filterMap.put("/student/addStudent","perms[student:add]");
        filterMap.put("/student/updateStudent","perms[student:update]");
        filterMap.put("/student/deleteStudent","perms[student:delete]");


        //添加到内置过滤器
        Sbean.setFilterChainDefinitionMap(filterMap);

        //设置登录的请求
        Sbean.setLoginUrl("/toLogin");
        //设置权限不足页面
        Sbean.setUnauthorizedUrl("/toUnauthorized");
        return  Sbean;
    }


    /**
     * DafauLtWebSecurityManager   管理全部用户 第二步
     * @param studentRealm  绑定realm对象
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("studentRealm") StudentRealm studentRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联StudentRealm
        securityManager.setRealm(studentRealm);
        return  securityManager;
    }


    //创建realm对象,需要自定义类 用来连接数据（第一步）
    @Bean()  //使用bean，用spring方式让方法自动获得配置好的对象
    public StudentRealm studentRealm(){
        return  new StudentRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

//    Qualifier的意思是合格者，通过这个标示，表明了哪个实现类才是我们所需要的；
//    @Autowired默认按照类型匹配注入bean，如果有多个实现类，搭配@Qualifier(“实现类名称”)表明注入的是哪一个实现类的bean：
//    比如：
//    @Autowired
//    @Qualifier(“TestServiceImpl1”)
//    private TestService testService; //注入的就是TestServiceImpl1这个实现类，注意如果该实现类使用了诸如@Service(“beanName”)这种注解，那么TestServiceImpl1就是beanName；
//
//    而@Resource默认按照名称匹配注入bean，而且可以使用name作为属性来确定实际注入的bean
//    比如：
//    @Resource(name = “TestServiceImpl1”)
//    private TestService testService;



}
