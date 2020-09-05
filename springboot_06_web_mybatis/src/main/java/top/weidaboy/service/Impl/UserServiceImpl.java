package top.weidaboy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weidaboy.mapper.UserMapper;
import top.weidaboy.pojo.User;
import top.weidaboy.service.IUserService;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    //自动加载Mapper类
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public User findUserByID(Integer id) {
        return userMapper.findUserByID(id);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int countUser() {
        return userMapper.countUser();
    }

    @Override
    public List<User> pageList(Integer startRec) {
        return userMapper.pageList(startRec);
    }
}
