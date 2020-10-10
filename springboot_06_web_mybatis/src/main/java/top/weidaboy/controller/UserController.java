package top.weidaboy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.weidaboy.pojo.User;
import top.weidaboy.service.IUserService;
import top.weidaboy.utils.RedisUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    public String allUser(Model model){
        //当前页
        int currentPage = 1;
        // totalRecord   总记录数
        int totalRecord=userService.countUser();;
        //  pageSize      每页记录数，固定  10
        int pageSize  =  10;
        //pageCount 总页数
        int pageCount = (totalRecord % pageSize==0) ? (totalRecord / pageSize):(totalRecord/pageSize+1) ;
        //起始条数
        int startRec = 0;
        System.out.println("-----------------------");
        System.out.println("起始条数:"+startRec);
        System.out.println("当前页:"+currentPage);
        System.out.println("总页数:"+ pageCount);

        //设置当前页码
        model.addAttribute("currentPage",currentPage);
        //设置总页数
        model.addAttribute("pageCount",pageCount);
        //获得当前需要查询的数据范围
        List<User> users = userService.pageList(startRec);

        model.addAttribute("users",users);
        return "allUser";
    }

    @RequestMapping("/page/{currentPage}")
    public String list(Model model,
                       @PathVariable("currentPage") int currentPage) {
        //             totalRecord   总记录数
        int totalRecord = userService.countUser();;
        //  pageSize      每页记录数，固定  10
        int pageSize  =  10;
        //pageCount 总页数
        int pageCount = (totalRecord % pageSize==0) ? (totalRecord / pageSize):(totalRecord/pageSize+1);
        //起始条数
        int startRec = 0;
        if(currentPage > 1){
            startRec = (currentPage-1)*pageSize;
        }
        System.out.println("-----------------------");
        System.out.println("起始条数:"+startRec);
        System.out.println("当前页:"+currentPage);
        System.out.println("总页数:"+ pageCount);

        //设置当前页码
        model.addAttribute("currentPage",currentPage);
        //设置总页数
        model.addAttribute("pageCount",pageCount);
        //获得当前需要查询的数据范围
        List<User> users = userService.pageList(startRec);

        model.addAttribute("users",users);

        return "allUser";
    }


    //使用分页插件+redis缓存
    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("/pagehelper/{currentPage}")
    public String pagelist(Model model,
                       @PathVariable("currentPage") int currentPage) {
        //起始条数
        int startRec = 0;
        //总页数
        Integer pageCount =0;
        //total 总条数
        int total =0;
        //总页数 确认页数范围
        long startTime = System.currentTimeMillis();    //获取开始时间
        if(redisUtils.get("pageCount") == null){
            System.out.println("正在从数据库提取数据....");
            List<User> users = userService.findAllUser();
            //total 总条数
            total = userService.countUser();
            // 总页数
            pageCount = ( total % 10 == 0 )? (total / 10) : (total / 10 + 1);

            for (int i = 0; i < users.size() ; i++) {
                redisUtils.lPush("users", JSON.toJSONString(users.get(i)));
            }
            redisUtils.set("total",total+"");
            redisUtils.set("pageCount",pageCount+"");
            long endTime = System.currentTimeMillis();    //获取结束时间
            System.out.println("从mysql数据库装载到redis缓存用时：" + (endTime - startTime) + "ms");    //输出程序运行时间
        }
        long startTimeRedis = System.currentTimeMillis();    //获取开始时间
        System.out.println("正在从redis缓存拿取数据");
        pageCount = Integer.parseInt(redisUtils.get("pageCount").toString());
        if(currentPage > pageCount) currentPage = pageCount;
        if(currentPage < 1 ) currentPage = 1 ;
        if(currentPage > 1){
            startRec = (currentPage-1) * 10;
        }
        //从redis中获取数据
        List<Object> users1 = redisUtils.lRange("users", startRec, startRec + 9);

        List<User> redisusers = new ArrayList<>();
        for (int i = 0; i < users1.size(); i++) {
            JSONObject userJson = JSONObject.parseObject( (String) users1.get(i) );
            User user = JSON.toJavaObject(userJson,User.class);
            redisusers.add(user);
        }
        long endTimeRedis = System.currentTimeMillis();    //获取结束时间
        System.out.println("从redis缓存获取数据时间：" + (endTimeRedis - startTimeRedis) + "ms");    //输出程序运行时间
        model.addAttribute("users",redisusers);

        //设置当前页码
        model.addAttribute("currentPage",currentPage);
        //设置总页数
        model.addAttribute("pageCount",pageCount);

        return "allUser";
    }




}
