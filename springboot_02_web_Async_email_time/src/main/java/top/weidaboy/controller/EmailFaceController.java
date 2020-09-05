package top.weidaboy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailFaceController {

    @RequestMapping("/send")
    public String toSendFace(){
        return "sendEmail";
    }

}
