package top.weidaboy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.weidaboy.pojo.Student;
import top.weidaboy.pojo.User;
import top.weidaboy.service.IStudentService;
import top.weidaboy.service.IUserService;
import top.weidaboy.utils.RedisUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/allstudent")
    public String allUser(Model model){
        List<Student> allStudent = studentService.findAllStudent();
        model.addAttribute("students",allStudent);
        return "allStudent";
    }


}
