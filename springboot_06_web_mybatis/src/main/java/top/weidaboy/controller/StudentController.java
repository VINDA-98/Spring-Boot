package top.weidaboy.controller;

;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.weidaboy.pojo.Student;
import top.weidaboy.service.IStudentService;
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
