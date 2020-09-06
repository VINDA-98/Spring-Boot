package top.weidaboy.service.Impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import top.weidaboy.service.ISmsService;
import java.util.Map;

@Service
public class SmsServiceImpl  implements ISmsService {
    @Override
    public boolean sendSms(String phone,String templateCode,Map<String,Object> map) {
        //连接阿里云
        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou",
                        "LTAI4Fz91i4NtNiEPAhp7e2r",
                        "RZGIr2FET0iv1Y7GH1bQqGNqtNcAHS");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);

        /**版本信息不做修改*/
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");

        //连接事件
        request.setSysAction("SendSms");

        /**
         * 参数：需要发送的手机号，验证码，签名、模板
         */
        //手机号
        request.putQueryParameter("PhoneNumbers", phone );
        //签名
        request.putQueryParameter("SignName", "Vinda的学习网站");
        //模板
        request.putQueryParameter("TemplateCode", templateCode);
        //验证码
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sendSms(String phone, String templateCode) {
        //连接阿里云
        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou",
                        "LTAI4Fz91i4NtNiEPAhp7e2r",
                        "RZGIr2FET0iv1Y7GH1bQqGNqtNcAHS");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);

        /**版本信息不做修改*/
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");

        //连接事件
        request.setSysAction("SendSms");

        /**
         * 参数：需要发送的手机号，验证码，签名、模板
         */
        //手机号
        request.putQueryParameter("PhoneNumbers", phone );
        //签名
        request.putQueryParameter("SignName", "Vinda的学习网站");
        //模板
        request.putQueryParameter("TemplateCode", templateCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
