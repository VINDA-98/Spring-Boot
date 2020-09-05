package top.weidaboy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.weidaboy.pojo.User;

import java.util.List;


@Mapper
@Repository //添加后就可以使用Autowired
public interface UserMapper {

    @Select("select *from user")
    List<User> findAllUser();

    @Select("select *from user where id = #{id}")
    User findUserByID(Integer id);

    @Insert("insert into user(id,password,username,sex,major,team) " +
            "values(#{id},#{password},#{username},#{sex}" +
            ",#{major},#{team}")
    int insertUser(User user);

    @Update("update user set username = #{username} where id = #{id}")
    int updateUser(User user);

    @Delete("delete user where id = #{id}")
    int deleteUser(Integer id);

    @Select("select count(id) from user")
    int countUser();

    List<User> pageList(@Param("startRec") Integer startRec);
}
