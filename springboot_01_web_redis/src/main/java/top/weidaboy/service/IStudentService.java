package top.weidaboy.service;



import top.weidaboy.entities.Student;

import java.util.List;

public interface IStudentService {

     List<Student> findAllStudents();

     Student findStudentById(Integer id);

     int insertStudent(Student student);

     int deleteStudent(Integer id);

     int updateStudent(Student student);

     /**
      * 登录方法
      */
     Student login(String sname, String password);

     /**
      * 账户验证
      * @param sname
      * @return
      */
     Student findStudentByName(String sname);
}
