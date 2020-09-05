package top.weidaboy.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import top.weidaboy.entity.Content;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {
    //查询天猫
    public List<Content> parseTM(String keywords) {
        String url = null;
        List<Content> contents = new ArrayList<>();
        try {
            //1、拼接搜索地址 中文还得解决编码

            url = "https://list.tmall.com/search_product.htm?q="+keywords+"&type=p&enc=utf-8";
            url =  URLDecoder.decode(url,"utf-8");
            System.out.println(url);
            //2、解析网页 设置好超时时间
            Document document = Jsoup.parse(new URL(url),30000);
            //3、使用js可以使用的方法 from jsoup
            Element element = document.getElementById("J_ItemList");
            if(element == null) {
                System.out.println("无返回内容！");
                contents.add(new Content("无","无","无"));
                return contents;
            }
            //4、获取li有序元素
            Elements allClass = element.getElementsByClass("product-iWrap");
            //5、打印元素
            for (Element aClass : allClass) {
//                System.out.println("-------------------------------------------------------------------------");
                //System.out.println(aClass);
                String title = aClass.getElementsByClass("productTitle").eq(0).text();
                String productPrice = aClass.getElementsByClass("productPrice").eq(0).text();
                String imgURL = aClass.getElementsByTag("img").attr("data-ks-lazyload");
                contents.add(new Content(title,productPrice,imgURL));
//                System.out.println("标题："+title);
//                System.out.println("价格："+productPrice);
//                System.out.println("图片："+imgURL);
                //ByTag（通过标签）：通过img标签获得图片内容 attr() 方法设置或返回被选元素的属性值
                //System.out.println("图片："+aClass.getElementsByTag("img").attr("src"));
                //网页加载使用的是data-ks-lazyload这个属性，延迟加载，不然找不到对应的src
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析失败");
        }
        return contents;
    }

    public static void main(String[] args) {
        new HtmlParseUtil().parseTM("情趣").forEach(System.out::println);
    }

//    //1、拼接搜索地址
//    String url = "https://list.tmall.com/search_product.htm?q=java";
//    //2、解析网页 设置好超时时间
//        try {
//        Document document = Jsoup.parse(new URL(url),30000);
//        //3、使用js可以使用的方法 from jsoup
//        Element element = document.getElementById("J_ItemList");
//        //4、获取li有序元素
//        Elements allClass = element.getElementsByClass("product-iWrap");
//        //5、打印元素
//        for (Element aClass : allClass) {
//            System.out.println("-------------------------------------------------------------------------");
//            //System.out.println(aClass);
//            String title = aClass.getElementsByClass("productTitle").eq(0).text();
//            String productPrice = aClass.getElementsByClass("productPrice").eq(0).text();
//            String imgURL = aClass.getElementsByTag("img").attr("data-ks-lazyload");
//            System.out.println("标题："+title);
//            System.out.println("价格："+productPrice);
//            //ByTag（通过标签）：通过img标签获得图片内容 attr() 方法设置或返回被选元素的属性值
//            //System.out.println("图片："+aClass.getElementsByTag("img").attr("src"));
//            //网页加载使用的是data-ks-lazyload这个属性，延迟加载，不然找不到对应的src
//            System.out.println("图片："+imgURL);
//
//        }
//
//    } catch (IOException e) {
//        e.printStackTrace();
//        System.out.println("解析失败");
//    }
}
