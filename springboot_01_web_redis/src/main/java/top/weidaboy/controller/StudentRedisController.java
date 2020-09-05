package top.weidaboy.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.entities.Student;
import top.weidaboy.service.IStudentService;
import top.weidaboy.utils.RedisUtils;
import java.util.List;


/**
 * string数据类型
 * 应用场景： 秒杀商品/点赞
 *
 * hash数据类型
 *  实际应用场景：电商网站中的商品详细信息
 *
 *  list数据类型
 *  应用场景： 商品的评论表
 *
 *  set数据类型
 *  应用场景： 微信朋友查看权限 /独立IP投票限制
 *
 *  zset数据类型
 *  应用场景： 商品的销售排行榜
 */
@RestController
public class StudentRedisController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IStudentService studentService;
    

    @GetMapping("setAndget/{k}&{v}")
    public String setAndget(@PathVariable("k")String k , @PathVariable("v")String v){
        redisUtils.set(k,v);
        return (String)redisUtils.get(k);
    }


    @GetMapping("findReidsStudent/{id}")
    public String setAndget(@PathVariable("id")Integer id){
        Student studentById = null;
        //第一步先去判断缓存中是否存在这个学生，如果不存在就从数据库拿
        String stukey = (String) redisUtils.get("stu"+id);
        if(stukey == null){
            System.out.println("正在从数据库单独提取对应ID学生信息");
            studentById = studentService.findStudentById(id);
            if(studentById != null) redisUtils.set("stu"+id,studentById.toString());
            else return "noStudent";

            return (String) redisUtils.get("stu"+id);
        }
        System.out.println("正在从redis单独提取对应ID学生信息");
        return  (String) redisUtils.get("stu"+id);
    }

    @GetMapping("redisAllStudent")
    public String redisAllStudent(){
        //第一步，先是获取到缓存数据中学生列表信息
        String stuListCacheKey = (String) redisUtils.get("stuListCache");
        //第二步，判断缓存数据中是否有值，没有值的情况下就从数据库中拿值。
        if(stuListCacheKey == null){
            System.out.println("从数据库中初始化所有学生数据....");
            List<Student> allStudents = studentService.findAllStudents();
            //第三步，如果遍历到的数据信息不为空，则刷新redis缓存
            if(allStudents != null){
                //添加学生列表
                //将学生了列表信息转换为json格式
                redisUtils.set("stuListCache", JSON.toJSONString(allStudents));
//                ListOperations<String, String> list = redisTemplate.opsForList();
//                for(Student s : allStudents){
//                    list.rightPush("stuListCache", JSON.toJSONString(s));
//                }
               // redisUtils.lPush("stuListCache", JSON.toJSONString(allStudents));
                return (String) redisUtils.get("stuListCache");
            }
            return  "noStudent";
        }
        //第四步 如果不为空，直接返回json格式的学生信息
        System.out.println("正在从redis数据库中获取所有学生数据....");
        return (String) redisUtils.get("stuListCache");
    }
}
