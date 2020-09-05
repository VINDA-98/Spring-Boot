package top.weidaboy.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import top.weidaboy.entities.Student;
import top.weidaboy.service.IStudentService;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//自定义StudentRealm类
public class StudentRealm extends AuthorizingRealm {

    @Resource
    private IStudentService studentService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("正在完成用户授权....");
        SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();

        //拿到当前对象: current 现时发生的
        Student currentStudent = (Student) subject.getPrincipal();
        //设置权限名称
        List<String> perms = new ArrayList<>();
        switch (currentStudent.getPerms()){
            case "1":perms.add("student:add");break;
            case "2":perms.add("student:update");break;
            case "3":perms.add("student:delete");break;
            //root权限
            case "4":perms.add("student:add");
                     perms.add("student:update");
                     perms.add("student:delete");
                    break;
        }

        //给授权
        //simpleInfo.addStringPermission("student:addStudent");
        simpleInfo.addStringPermissions(perms);

        return simpleInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //用户名+密码
        Student student = studentService.findStudentByName(token.getUsername());

        if (student == null) {
            return null;
        }


        /**
         * 密码认证，shiro做  盐值加密
         * 第一个参数 Principal就是要传递的当前对象
         * 第二个参数就是传递对象的密码
         *
         */

        return new SimpleAuthenticationInfo(student, student.getPassword(), "");
    }



}
