package top.weidaboy.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import top.weidaboy.util.EmailUtils;

import javax.mail.MessagingException;
import javax.security.auth.Subject;
import javax.validation.constraints.Email;

@Validated
@Service
public class AsyncService {

    @Autowired
    private EmailUtils emailUtils;

    public void sendSyncEmail(String subject, String text,String setToPerpel) throws MessagingException {
        try {
            Thread.sleep(3000);
        }catch (InterruptedException  e){
            e.printStackTrace();
        }
        System.out.println("同步：邮件正在发送....");
        emailUtils.SendMain(subject,text,setToPerpel);

    }

    /**
     * 声明这是一个异步服务
     */
    @Async
    public void sendAsyncEmail(String subject, String text,String setToPerpel) throws MessagingException{
        try {
            Thread.sleep(3000);
        }catch (InterruptedException  e){
            e.printStackTrace();
        }
        System.out.println("异步：邮件正在发送....");
        emailUtils.SendMain(subject,text,setToPerpel);

    }
}
