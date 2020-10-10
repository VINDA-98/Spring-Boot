package top.weidaboy.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.Field;

//类名注解
@Target(ElementType.TYPE)  //在类上使用
@Retention(RetentionPolicy.RUNTIME)  //运行时候生效
@Inherited()  //子类可以继承父类中的该注解
@interface MyClassAnnotation {
    String value();
}

//属性注解
@Target(ElementType.FIELD)  //在类上使用
@Retention(RetentionPolicy.RUNTIME)  //运行时候生效
@Inherited()  //子类可以继承父类中的该注解
@interface MyFieldAnnotation {
    String columnName(); //列名
    String type(); //类型
    int length(); //长度
}

//方法注解
@Target(ElementType.METHOD)  //在类上使用
@Retention(RetentionPolicy.RUNTIME)  //运行时候生效
@Inherited()  //子类可以继承父类中的该注解
@interface MyMethodAnnotation {
    String value();
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@MyClassAnnotation("db_student")
class Student1 implements Serializable {

    @MyFieldAnnotation(columnName = "id",type = "int",length = 10)
    private Integer id;
    @MyFieldAnnotation(columnName = "name",type = "varchar",length = 20)
    private String name;
    @MyFieldAnnotation(columnName = "py",type = "varchar",length = 10)
    private String py;
}


public class test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        //通过反射获取student Class对象
        Class student1 = Class.forName("top.weidaboy.mapper.Student1");
        //通过反射获取作用于student1类上所有注解
        Annotation[] annotations = student1.getAnnotations();
        System.out.println("annotations:");
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
        //获得注解的value值 ----> 获取指定注解！  要强转成我们自己定义的注解类型
        MyClassAnnotation classAnnotation = (MyClassAnnotation) student1.getAnnotation(MyClassAnnotation.class);
        System.out.println("类上的注解为："+classAnnotation.value());

        //获得属性的value值  不要用getField()，私有属性获取不到
        Field py = student1.getDeclaredField("py");
        //获取属性上的注解信息
        MyFieldAnnotation annotation = py.getAnnotation(MyFieldAnnotation.class);
        System.out.println(annotation.toString());
        System.out.println("columnName："+annotation.columnName());
        System.out.println("type："+annotation.type());
        System.out.println("length："+annotation.length());

    }
}


