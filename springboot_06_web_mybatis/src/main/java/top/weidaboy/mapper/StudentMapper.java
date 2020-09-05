package top.weidaboy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.weidaboy.pojo.Student;
import top.weidaboy.pojo.User;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    @Select("select *from student")
    List<Student> findAllStudent();
}
