package top.weidaboy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weidaboy.pojo.Student;
import top.weidaboy.service.IStudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/echarts")
public class EchartsController {

    @Autowired
    IStudentService studentService;

    @RequestMapping(value = "/allstudent")
    @ResponseBody
    public Map<String,Object> echars01(Model model){
        Map<String,Object> map = new HashMap<>();
        List<Student> students =studentService.findAllStudent();
        map.put("students",students);
        map.put("skirt","裙子");
        map.put("nums",30);
        System.out.println(map);
        return map;
    }
}
