package top.weidaboy.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.entities.Student;
import top.weidaboy.service.IStudentService;

import java.util.List;

@Api(tags = "学生管理操作列表")  //指定接口
@RestController
@RequestMapping("/student")
public class StudentSwaggerController {
    @Autowired
    private IStudentService studentService;
    /**
     * @GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。
     * @PostMapping是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写。
     * 其实一般项目中是要区分get和post请求的，未避免忘记写method，应该要写getmapping或者postmapping
     * @return
     */
    @GetMapping("/findAllStudents")
    public List<Student> findAllStudents(){
        return studentService.findAllStudents();
    }

    /**
        返回值存在实体类，就会被扫描到swagger中
        student/findStudentByID/1
    */
    @GetMapping("/{sanme}")
    @ApiOperation(value = "查询学生",notes = "根据学生姓名查询写生信息")
    public Student getOne(@ApiParam(value = "学生姓名" , required = true) @PathVariable("sname") String sname){
            return studentService.findStudentByName(sname);
    }
}
