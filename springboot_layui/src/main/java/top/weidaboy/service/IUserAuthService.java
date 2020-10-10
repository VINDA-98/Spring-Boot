package top.weidaboy.service;


import top.weidaboy.domain.UserAuth;

public interface IUserAuthService extends AbstractIService<UserAuth> {

    UserAuth login(String username, String password);
}
