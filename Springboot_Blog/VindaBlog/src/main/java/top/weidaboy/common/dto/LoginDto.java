package top.weidaboy.common.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

//因为登录只用到了两个字段 ，新建一个dto类来做登录处理
@Data
@Validated
public class LoginDto {

    @NotNull(message="用户名不能为空")
    private String username;

    @NotNull(message="密码不能为空")
    private String password;

}
