package top.weidaboy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weidaboy.mapper.StudentMapper;
import top.weidaboy.mapper.UserMapper;
import top.weidaboy.pojo.Student;
import top.weidaboy.pojo.User;
import top.weidaboy.service.IStudentService;
import top.weidaboy.service.IUserService;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements IStudentService {
    //自动加载Mapper类
    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> findAllStudent() {
        return studentMapper.findAllStudent();
    }

    @Override
    public User findStudentByID(Integer id) {
        return null;
    }

    @Override
    public int inserStudent(Student student) {
        return 0;
    }

    @Override
    public int updateStudent(Student student) {
        return 0;
    }

    @Override
    public int deleteStudent(Integer id) {
        return 0;
    }

    @Override
    public int countStudent() {
        return 0;
    }

    @Override
    public List<Student> pageList(Integer startRec) {
        return null;
    }
}
