package top.weidaboy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.entities.Student;
import top.weidaboy.service.IStudentService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class StudentController {

    @Resource
    private IStudentService studentService;

    /**
     * 登录转发
     * @param model
     * @return
     */
    @RequestMapping({"/","/index.html","/toLogin"})
    public String toindex(Model model){
        model.addAttribute("msg","加油！");
        return "login";
    }

    /**
     * 登录判断
     * @param model
     * @return
     */
    @RequestMapping("/userlogin")
    public String tologin(Model model,String sname,String password){
        System.out.println(sname+"   "+ password);
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(sname,password);
        //验证用户,执行登录方法
        try {
            subject.login(token);  //允许登录

            Subject currentSubject = SecurityUtils.getSubject();
            Session session = currentSubject.getSession();

            System.out.println(session);
            //session.setAttribute("loginUser" ,session);
            List<Student> allStudents = studentService.findAllStudents();
            model.addAttribute("allStudents",allStudents);
            return "student/allStudent";
        }catch (UnknownAccountException e){ //用户名不存在
            model.addAttribute("errormsg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){ //用户名不存在
            model.addAttribute("errormsg","输入密码错误");
            return "login";
        }
    }

    /**
     * 未授权页面
     * @param model
     * @return
     */
    @RequestMapping("/toUnauthorized")
    public String toUnauthorized(Model model){
        return "401";
    }


    @RequestMapping("/student/addStudent")
    public String addStudent(Model model){
        model.addAttribute("msg","加油！");
        return "student/addStudent";
    }

    @RequestMapping("/student/updateStudent")
    public String updateStudent(Model model){
        model.addAttribute("msg","加油！");
        return "student/updateStudent";
    }

    @RequestMapping("/student/deleteStudent")
    public String deleteStudent(Model model){
        model.addAttribute("msg","加油！");
        return "student/deleteStudent";
    }


}
