package top.weidaboy.service;

import org.apache.ibatis.annotations.Param;
import top.weidaboy.pojo.User;

import java.util.List;

public interface IUserService {
    /**
     * 查询所有用户信息
     * @return
     */
    List<User> findAllUser();

    /**
     * 通过ID查询用户信息
     * @return
     */
    User findUserByID(Integer id);

    /**
     * 插入新的用户信息
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 通过ID删除用户信息
     * @param id
     * @return
     */
     int deleteUser(Integer id);

    /**
     * 计算总条数
     * @return
     */
     int countUser();

    /**
     *  计算分页
     */
    List<User> pageList(Integer startRec);
}
