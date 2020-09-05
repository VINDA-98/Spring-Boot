package top.weidaboy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class WeidaboyApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {
        //邮件发送测试
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("通知-明天要打飞机");
        message.setText("今晚我们去吃夜宵吗？");

        message.setTo("目的邮箱@qq.com","发送者@qq.com");
        message.setFrom("发送者@qq.com");
        mailSender.send(message);
    }

    @Test
    public void contextLoads2() throws MessagingException {
        //邮件设置2：一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("通知-明天开学啦");
        helper.setText("<b style='color:red'>今天 11:30 我们去打代码</b>",true);

        //发送附件
        helper.addAttachment("1.png",new File("src/main/resources/static/img/1.png"));
        helper.addAttachment("2.png",new File("src/main/resources/static/img/2.png"));

        helper.setTo("目的邮箱@qq.com");
        helper.setFrom("发送邮箱@qq.com");

        mailSender.send(mimeMessage);
    }


}
