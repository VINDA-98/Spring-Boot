package top.weidaboy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;
import top.weidaboy.entities.Student;
import top.weidaboy.service.IStudentService;

@RestController
public class StudentChcheController {

    @Autowired
    private IStudentService studentService;
        /*
    *   @Cacheable  将方法的运行结果进行缓存；
    *               第二次再要相同的数据，直接从缓存中获取，不再调用方法；
        @CacheEvict 移除缓存
        @CachePut   修改了数据库的某个数据，同时更新缓存*/

    /*
     * Restful风格 get到id查询学生信息，是否从缓存*/
    @GetMapping("/student/findStudentByID/{id}")
    public String findStudentByID(@PathVariable("id") Integer id){
        Student studentById = studentService.findStudentById(id);
        if(studentById == null) return "NoStudent";
        return studentById.toString();
    }


}
