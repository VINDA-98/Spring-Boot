package top.weidaboy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.weidaboy.entities.Student;

import java.util.List;

@Mapper      //开启扫描包之后这里就不用注释了
@Repository  //使用Repository就可以给StudentMapper这个类添加@Autowreid注解不爆红
public interface StudentMapper {
    /**
     * 查询所有学生
     * @return
     */
    //@Select("select * from student")
    List<Student> findAllStudents();

    @Select("select * from student where id=#{id}")
    Student findStudentById(Integer id);

    @Select("select * from student where sname=#{sname}")
    Student findStudentByName(String sname);


    @Insert("insert into student(sname,sage) values(#{sname},#{sage})")
    int insertStudent(Student student);


    @Delete("delete from student where id=#{id}")
    int deleteStudent(Integer id);


    @Update("update student set sname=#{sname},sage=#{sage} where id=#{id}")
    int updateStudent(Student student);

    /**
     * 登录方法
     * @param sname
     * @param password
     * @return
     */
    Student login(@Param("sname") String sname, @Param("password") String password);

}
