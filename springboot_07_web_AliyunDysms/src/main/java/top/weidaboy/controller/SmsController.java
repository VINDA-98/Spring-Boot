package top.weidaboy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.config.SmsConfig;
import top.weidaboy.service.ISmsService;
import top.weidaboy.utils.RedisUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@CrossOrigin //跨域支持
@RestController
public class SmsController {

    @Autowired
    ISmsService smsService;

    @Autowired
    SmsConfig smsConfig;

    @Autowired
    RedisUtils RedisUtils;

    @GetMapping("/sendsms/{phone}")
    public String sendCode(@PathVariable("phone")String phone){
        //从redis缓存中拿取用户手机号码，在实际开发中应该加上手机号码和用户id作为唯一的key
        String phone1 = (String) RedisUtils.get(phone);
        //确认没有这个号码
        if(StringUtils.isEmpty(phone1)){
            //存入数据库
            RedisUtils.set("phone",phone);
        }
        //查询是否在5分钟前发过该短信,验证码应该也是唯一的，加上用户ID
        String code = (String) RedisUtils.get("code");
        //如果没有这个验证码
        if(StringUtils.isEmpty(code)){
            //新建验证码：
             code = UUID.randomUUID().toString().substring(0, 4);
        }else {
            RedisUtils.set("code",code, (long) (5 * 60));
            return "手机号码为："+ phone + "的用户，在5分钟内不能重复发送验证码";
        }


        //调用发送方法
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用发送：
        boolean b = smsService.sendSms(phone, smsConfig.YZMCODE, param);
        if (b){
            //发送成功再将验证码存入缓存
            RedisUtils.set("code",code, (long) (5 * 60));
            return "手机号码为："+ phone + "的用户，成功发送验证码："+code;
        }else return "手机号码为："+ phone + "的用户，发送短信失败";
    }


}
