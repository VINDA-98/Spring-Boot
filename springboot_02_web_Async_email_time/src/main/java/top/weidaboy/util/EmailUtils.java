package top.weidaboy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

public class EmailUtils {

    @Autowired
    JavaMailSenderImpl mailSender;

    public void SendMain(String subject,String text, String setToPerpel) throws MessagingException {
        //邮件设置2：一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(subject);
        helper.setText(text,true);

        //发送附件
        helper.addAttachment("1.png",new File("src/main/resources/static/img/1.png"));
        helper.addAttachment("2.png",new File("src/main/resources/static/img/2.png"));

        //选择收件人
        helper.setTo(setToPerpel);
        helper.setFrom("760319460@qq.com");

        mailSender.send(mimeMessage);
    }
}
