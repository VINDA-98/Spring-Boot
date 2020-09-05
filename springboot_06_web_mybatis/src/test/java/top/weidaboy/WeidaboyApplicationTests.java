package top.weidaboy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.weidaboy.pojo.User;
import top.weidaboy.service.IUserService;
import top.weidaboy.utils.RedisUtils;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WeidaboyApplicationTests {

    @Autowired
    IUserService userService;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() {
//        System.out.println(userService.countUser());
//        userService.pageList(10).forEach(System.out::println);
        /*startPage（） 参数
         * int pageNum  : 当前页码
         * int pageSize ：页码大小
         * 这句话要在前面啊
         * */
//        PageHelper.startPage(2, 5);
//        //先获取所有的内容
//        List<User> users = userService.findAllUser();
//        //Page 是一个类
//        //System.out.println("Total: " + ((Page) users).getTotal());
//        for (User user : users) {
//            System.out.println("Name: " + user.getUsername());
//        }

//        redisUtils.set("test","ok");
//        System.out.println(redisUtils.get("test"));
    }

    @Test
    public void test01(){


    }

}
