package top.weidaboy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

@SpringBootTest
class SpringbootLayuiApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 使用文件流实现文件复制
     * @author smy
     * @throws Exception
     */
    public void copy(InputStream in,OutputStream out) throws Exception{
        byte[] data = new byte[1024*10];
        int len = -1;
        while((len = in.read(data))!=-1){
            out.write(data,0,len);
        }

        in.close();
        out.close();
    }

    /**
     * 使用缓冲流加快读写效率
     *
     * 缓冲流是一对高级流，使用它们可以提高读
     * 写效率。
     *
     * 高级流:高级流用来处理其他流，高级流不能
     * 独立存在，因为没有意义，使用高级流的目的
     * 是为了解决某些读写操作的复杂度。简化我们
     * 的读写操作。
     *
     * 低级流:数据源明确，真实负责读写数据的流。
     *
     * 读写操作一定需要低级流，但不一定非要使用
     * 高级流。
     * @author SMY
     * @throws Exception
     */
    public  void CopyDemo2(InputStream bis,OutputStream bos) throws Exception{
        int d = -1;
        /*
         * 缓冲流内部维护了一个字节数组作为
         * 缓冲。当我们调用read()方法读取一个
         * 字节时，缓冲流会一次性读取若干字节
         * 并存入缓冲区，然后返回第一个字节，当
         * 我们再次调用read方法读取一个字节时，
         * 它会立即将缓冲区中第二个字节返回，
         * 直到所有字节都返回后，才会再次发生
         * 实际的读取操作，读取一组字节放入缓冲
         * 区。
         * 所以本质上还是通过一次读取若干字节，
         * 减少读取次数提高的读取效率。
         */
        while((d=bis.read())!=-1){
            bos.write(d);
        }
        bis.close();
        bos.close();
    }


    @Test
    public void test02() throws Exception{


    }

    @Test
    public void test01() throws Exception{
        File file=new File("D:\\officetest");
        //判断目录是否存在
        if(!(file.exists()&&file.isDirectory())){
            throw new Exception("目录不存在");
        }
        // 找到文件夹下面的 .java 文件
        File[] files=file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                // TODO Auto-generated method stub
                return name.endsWith(".docx");
            }
        });
        System.out.println("本次复制文件共有："+files.length+"个");
        // 复制文件
        // 找到目标目录
        File targetfile= new File("D:\\officetest\\copy");
        //判断目录是否存在，不存在创建
        if(!(targetfile.exists()&&targetfile.isDirectory())){
            targetfile.mkdirs();
        }
        //添加修改时间
        long count = 0;
        // 替换文件名字
        for(File f:files){
//                1.使用文件流实现文件复制
            //lastModified 获取指定文件的最后修改日期

            Date d = new Date(f.lastModified());
            System.out.println("原始修改日期为："+d.toString());

            //40 - 50
            int num = (int)Math.random()*40+10;
            count += 60*60*2*num;
            long l = d.getTime() + count;
            boolean falg =  f.setLastModified(l);//需要long类型
            System.out.println("修改："+falg);
            long time = d.getTime();
            System.out.println(time);
            d = new Date(f.lastModified());
            System.out.println("修改后日期："+d.toString());
            System.out.println("----------------------------");

            String targetFilename=f.getName().replace(".docx", ".docx");
//                2.使用缓冲流加快读写效率
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(f));
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(targetfile,targetFilename)));
            CopyDemo2(bis,bos);
        }
    }


}
