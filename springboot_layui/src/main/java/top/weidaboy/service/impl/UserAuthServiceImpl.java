package top.weidaboy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weidaboy.dao.UserAuthDao;
import top.weidaboy.domain.UserAuth;
import top.weidaboy.service.IUserAuthService;
import top.weidaboy.util.UserContext;


@Service
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth> implements IUserAuthService {
    @Autowired
    private UserAuthDao dao;

    @Override
    public UserAuth login(String username, String password) {
        UserAuth currentUser = dao.checkLogin(username, password);
        if (currentUser == null) {
            throw new RuntimeException("帐号或密码错误!");
        }
        UserContext.setCurrentUser(currentUser);
        return currentUser;
    }
}
