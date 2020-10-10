package top.weidaboy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.weidaboy.pojo.Student;
import top.weidaboy.service.IUserService;
import top.weidaboy.utils.RedisUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
class WeidaboyApplicationTests {

    @Autowired
    IUserService userService;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() {
//        System.out.println(userService.countUser());
//        userService.pageList(10).forEach(System.out::println);
        /*startPage（） 参数
         * int pageNum  : 当前页码
         * int pageSize ：页码大小
         * 这句话要在前面啊
         * */
//        PageHelper.startPage(2, 5);
//        //先获取所有的内容
//        List<User> users = userService.findAllUser();
//        //Page 是一个类
//        //System.out.println("Total: " + ((Page) users).getTotal());
//        for (User user : users) {
//            System.out.println("Name: " + user.getUsername());
//        }

//        redisUtils.set("test","ok");
//        System.out.println(redisUtils.get("test"));
    }

    @Test
    public void test01(){
        System.out.println("Main代码块被加载");
        //1、主动引用
        Son s = new Son();
    }
    @Test
    public void test02() throws ClassNotFoundException {
//        System.out.println("Main代码块被加载");
//        System.out.println(Son.a);  //调用父类属性，子类无需初始化
//        //2、反射引用
//        System.out.println(Class.forName("top.weidaboy.WeidaboyApplicationTests.Father"));
//        System.out.println(Class.forName("top.weidaboy.WeidaboyApplicationTests.Son"));
        //获取系统类的加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader() ;
        System.out.println("系统类加载器:"+systemClassLoader);

        //获取系统类加载器的父类加载器-->扩展类加载器
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println("系统类的父类（扩展类）加载器:"+parent);

        //获取办展类加载器的父类加载器-->根加载器(C/c++)
        ClassLoader parent1 = parent.getParent() ;
        System.out.println("扩展类的父类（根类）加载器:"+parent1);

        //测试当前类是哪个类加载器加载的
        ClassLoader classLoader = Class.forName("top.weidaboy.pojo.Student").getClassLoader();
        System.out.println("当前类的加载器:"+classLoader);

        //测试Object类是哪个类加载器加载的
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        System.out.println("Object类的加载器"+classLoader);

        //获取系统类加载器可以加载的路径
        System.out.println(System.getProperty("java.class.path"));
    }

    @Test
    public void test03() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> stuClass = Class.forName("top.weidaboy.pojo.Student");
        //获取名称
        System.out.println(stuClass.getName());
        System.out.println(stuClass.getSimpleName());

        System.out.println("Fields:");
        //获取属性
        //只能找到public属性
        Field[] fields = stuClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        //获取到私有属性
        System.out.println("DeclaredFields:");
        Field[] declaredFields = stuClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
        
        //获取所有方法，Object方法、继承方法和本类公有方法等
        System.out.println("Methods:");
        Method[] methods = stuClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //获取本类的所有方法，包括私有
        System.out.println("declaredMethods:");
        Method[] declaredmethods = stuClass.getDeclaredMethods();
        for (Method method : declaredmethods) {
            System.out.println(method);
        }

        System.out.println("指定方法：");
        //获取指定方法：
        System.out.println(stuClass.getMethod("getName", null));
        //防止重载，添加参数
        System.out.println(stuClass.getMethod("setName", String.class));

        //获取所有的构造器
        System.out.println("constructors:");
        Constructor<?>[] constructors = stuClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        System.out.println("declaredconstructors:");
        Constructor<?>[] declaredconstructors = stuClass.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredconstructors) {
            System.out.println(constructor);
        }

        //获得指定构造器
        System.out.print("指定构造器constructors:");
        System.out.println(stuClass.getDeclaredConstructor(Integer.class, String.class, String.class));

    }


    @Test
    public void test04() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //获取class对象
        Class<?> stuclass = Class.forName("top.weidaboy.pojo.Student");
        //使用构造器创建对象
        Constructor<?> constructor = stuclass.getDeclaredConstructor(Integer.class, String.class, String.class);
        //使用反射来新建对象，方法：newInstance
        Student student = (Student)constructor.newInstance(1, "Vinda", "WD");
        System.out.println("student:" +  student);

        //使用反射来获取指定
        Method setName = stuclass.getDeclaredMethod("setName", String.class);
        //激活方法 invoke ：激活
        setName.invoke(student,"VINDA");
        System.out.println("student:" +  student);

        //使用反射来操作属性
        Field name = stuclass.getDeclaredField("name");
        //没有开启权限前：can not access a member of class top.weidaboy.pojo.Student with modifiers "private"
        //开启权限后：正常访问,不允许直接操作私有属性，开启权限也相当于关闭安全检测
        name.setAccessible(true);
        name.set(student,"DAGE");
        System.out.println("student:" +  student);


    }

    @Test
    public void test05(){
        //反射操作注解
        //使用自定义注解

    }
    static class Father{
        static int a = 100;
        static {
            System.out.println("父类代码块被加载");
        }
    }
    static class Son extends Father{
        static int b = 100;
        static {
            System.out.println("子类代码块被加载");
        }
    }

    @Test
    public  void test07(){
        //分子：上一个分子分母的和，分母是上一个分子
        double fenzi = 2;
        double fenmu =1;
        double sum = fenzi/fenmu;
        double temp = 0;
        for (int i = 0 ; i<20 ; i++){
            System.out.print("  "+fenzi+"/"+fenmu+" + ");
            temp = fenmu;
            fenmu = fenzi;
            fenzi = fenzi + temp;
            sum += fenzi/fenmu;
        }
        System.out.println();
        System.out.print(sum);
    }
}
