package top.weidaboy.service;

import java.util.Map;

public interface ISmsService {
    /**
     * 发送验证短信需求
     * @param phone  发送手机号
     * @param templateCode  发送模板代码
     * @param map   模板中需要的代码块
     * @return
     */
    boolean sendSms(String phone,String templateCode,Map<String,Object> map);

    /**
     * 发送无代码短信需求
     * @param phone          需要发送到的手机号码
     * @param templateCode   发送的短信模板
     * @return
     */
    boolean sendSms(String phone,String templateCode);
}
