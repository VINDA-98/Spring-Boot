package top.weidaboy.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.weidaboy.entity.Content;
import top.weidaboy.util.ESutils;
import top.weidaboy.util.HtmlParseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {

    @Autowired
    private HtmlParseUtil htmlParseUtil;

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Autowired
    private ESutils eSutils;

    //往es添加从天猫获取的数据
    public boolean addGoods(String keywords) {
        //1、获取数据
        List<Content> contents = htmlParseUtil.parseTM(keywords);
        //2、添加内容，可以自己封装一个ES工具类，记得先有索引（必须全部是小写）
        eSutils.addData("tm_goods", contents);
        return true;
    }

    /**
     * 返回某个索引的全部数据
     *
     * @param index
     * @return
     */
    public SearchHit[] allIndex(String index) {
        return eSutils.getmatchAllQuery(index);
    }

    /**
     * 显示分页数据
     */
    public List<Map<String, Object>> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        ArrayList<Map<String, Object>> contents =new ArrayList<>();
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("tm_goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //获得结果
        SearchResponse searchResponse = null;
        try {
            //执行搜索
            searchRequest.source(sourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
                contents.add(hit.getSourceAsMap());
            }
            return contents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }


    /**
     * 显示高亮
     */
    public List<Map<String, Object>> searchPageHighlight(String keyword, Integer pageNo, Integer pageSize) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        ArrayList<Map<String, Object>> contents =new ArrayList<>();
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("tm_goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //选择需要高亮的字段
        highlightBuilder.field("title");
        //关闭多个高亮显示
        highlightBuilder.requireFieldMatch(false);
        //设置修饰高亮前置字段样式
        highlightBuilder.preTags("<span style='color:red'>");
        //设置修饰高亮后缀字段样式
        highlightBuilder.postTags("</span>");
        //装载到sourceBuilder 
        sourceBuilder.highlighter(highlightBuilder);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //获得结果
        SearchResponse searchResponse = null;
        try {
            //执行搜索
            searchRequest.source(sourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                //System.out.println(hit.getSourceAsMap());
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                //获取需要高亮的字段
                HighlightField title = highlightFields.get("title");
                //获取原来的结果
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //解析高亮字段
                if(title != null){
                    Text[] fragments = title.fragments();
                    String n_title = "";
                    for (Text fragment : fragments) {
                        n_title += fragment;
                    }
                    //高亮字段替换原来内容
                    sourceAsMap.put("title",n_title);
                }
                contents.add(sourceAsMap);
            }
            return contents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}

