package top.weidaboy.controller;


import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.service.ContentService;

import java.util.List;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    private ContentService contentService ;

    @GetMapping("/parse/{keyword}")
    public boolean getKeyword(@PathVariable("keyword") String keyword){
        return  contentService.addGoods(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> allIndex(@PathVariable("keyword")String keyword,
                                             @PathVariable("pageNo")Integer pageNo,
                                             @PathVariable("pageSize")Integer pageSize){
        System.out.println(keyword+"  "+pageNo+" "+pageSize);
        List<Map<String, Object>> maps = contentService.searchPage(keyword, pageNo, pageSize);
        return  maps;
    }

}
