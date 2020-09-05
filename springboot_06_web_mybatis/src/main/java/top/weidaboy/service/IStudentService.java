package top.weidaboy.service;

import top.weidaboy.pojo.Student;
import top.weidaboy.pojo.User;

import java.util.List;

public interface IStudentService {
    /**
     * 查询所有用户信息
     * @return
     */
    List<Student> findAllStudent();

    /**
     * 通过ID查询用户信息
     * @return
     */
    User findStudentByID(Integer id);

    /**
     * 插入新的用户信息
     * @param student
     * @return
     */
    int inserStudent(Student student);

    /**
     * 更新用户信息
     * @param student
     * @return
     */
    int updateStudent(Student student);

    /**
     * 通过ID删除用户信息
     * @param id
     * @return
     */
     int deleteStudent(Integer id);

    /**
     * 计算总条数
     * @return
     */
     int countStudent();

    /**
     *  计算分页
     */
    List<Student> pageList(Integer startRec);
}
