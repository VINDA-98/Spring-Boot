package top.weidaboy.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import top.weidaboy.common.lang.Result;
import top.weidaboy.entity.User;
import top.weidaboy.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author From：Vinda
 * @since 2020-09-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }


}
