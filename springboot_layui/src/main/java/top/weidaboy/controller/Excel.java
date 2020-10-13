package top.weidaboy.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.Scanner;

public class Excel {
        public static void main(String[] args) throws Exception {
            System.out.println("来自诸葛老达的提示（请输入要修改文件路径）：");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.next();
            File file = new File(filename);
            //判断目录是否存在
            if(!(file.exists()&&file.isDirectory())){
                throw new Exception("目录不存在");
            }
            // 找到文件夹下面的 .docx 文件
            File[] files=file.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    // TODO Auto-generated method stub
                    return name.endsWith(".excle");
                }
            });

            System.out.println("本次复制文件共有："+files.length+"个");
            System.out.println("----------------------------");
            //添加修改时间
            long count = 0;
            System.out.println("1增加时间，2减少时间");
            int i = scanner.nextInt();

            if(i==1){
                for(File f:files) {
//                1.使用文件流实现文件复制
                    //lastModified 获取指定文件的最后修改日期
                    Date d = new Date(f.lastModified());
                    System.out.println("原始文档日期为：" + d.toString());
                    //50 - 60
                    Date dateNow = new Date();
                    int num = (int) Math.random() * 50 + 10;
                    count += 60 * 60 * 2 * num ;
                    long l = dateNow.getTime() + count;
                    boolean falg = f.setLastModified(l);//需要long类型
                    //System.out.println("修改：" + falg);
                    d = new Date(f.lastModified());
                    System.out.println("修改后的日期为：" + d.toString());
                    System.out.println("----------------------------");
                }
            }

            if(i==2){
                for(File f:files) {
//                1.使用文件流实现文件复制
                    //lastModified 获取指定文件的最后修改日期

                    Date d = new Date(f.lastModified());
                    System.out.println("原始文档日期为：" + d.toString());


                    //50 - 60
                    Date dateNow = new Date();
                    int num = (int) Math.random() * 50 + 10;
                    long sum = 60 * 60 * 2 * 1000;
                    count += 60 * 60 * 2 * num ;
                    long l = dateNow.getTime() - (sum-count);
                    boolean falg = f.setLastModified(l);//需要long类型
                    //System.out.println("修改：" + falg);

                    d = new Date(f.lastModified());
                    System.out.println("修改后的日期为：" + d.toString());
                    System.out.println("----------------------------");
                }
            }

        }
}
