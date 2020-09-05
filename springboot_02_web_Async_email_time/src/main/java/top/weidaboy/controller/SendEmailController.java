package top.weidaboy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.service.AsyncService;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class SendEmailController {

    @Autowired
    private AsyncService asyncService;


    /**
     * 发送同步邮件
     */
    @GetMapping("/sendSyncEmail")
    public String sendSyncEmail(@RequestParam("Subject") String subject,
                                @RequestParam("content") String content,
                                @RequestParam("people") @Email(message = "邮箱格式地址有误") String setToPerpel)
            throws MessagingException {
        try {
            System.out.println(subject);
            System.out.println(content);
            System.out.println(setToPerpel);
            asyncService.sendSyncEmail(subject,content,setToPerpel);
        }catch (MessagingException e){
            return "邮箱地址有误";
        }
        return "发送成功";
    }

    /**
     * 发送异步邮件
     */
    @GetMapping("/sendAsyncEmail")
    public String sendAsyncEmail(
            @RequestParam("ASubject") String subject,
            @RequestParam("Acontent") String content,
            @RequestParam("Apeople") @Email(message = "邮箱格式地址有误") String setToPerpel
            ){
        try {
            System.out.println(subject);
            System.out.println(content);
            System.out.println(setToPerpel);
            asyncService.sendAsyncEmail(subject,content,setToPerpel);
        }catch (MessagingException e){
            return "邮箱地址有误";
        }
        return "发送成功";
    }


    /**
     * 定时发送邮件
     *  //秒   分   时     日   月   周几
     */
    @Scheduled(cron = "30 30 22 1/1 * ? ")
    public String EmailScheduled(){

        //设置日期格式
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String nowTime = sm.format(new Date());
        try {
            asyncService.sendAsyncEmail(
                    "今日邮件",
                    "你好渣男一号",
                    "1280900632@qq.com");
        }catch (MessagingException e){
            return "邮箱地址有误";
        }
        return nowTime + "  的定时邮件已经为您发送";
    }


}
