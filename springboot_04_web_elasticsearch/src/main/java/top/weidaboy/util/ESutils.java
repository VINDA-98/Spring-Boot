package top.weidaboy.util;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.weidaboy.entity.Content;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ESutils {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    //添加索引
    public void addIndex(String index){
        //发起创建索引请求,索引 --->  数据库 索引必须是小写哈~
        CreateIndexRequest request = new CreateIndexRequest("index");
        CreateIndexResponse response = null;
        try {
            //es服务器执行请求 indicesClient , 请求后获得对应响应
            response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("response："+response);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求发生异常！！！");
        }
    }

     /**
     * 判断是否存在索引
     */
    public boolean isHaveIndex(String index){
        //发起获取引请求,索引 --->  数据库 索引必须是小写哈~
        GetIndexRequest request = new GetIndexRequest(index);
        //es服务器执行请求 indicesClient , 请求后获得对应响应
        boolean b = false;
        try {
            b = client.indices().exists(request, RequestOptions.DEFAULT);
            if(b) System.out.println("是否存在："+b);
            else System.out.println("是否存在："+b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求发生异常！！！");
        }
        return b;
    }

    /**
     * 删除索引
     */
    public void deleteIndex(String Index){
        //发起删除引请求,索引 --->  数据库 索引必须是小写哈~
        DeleteIndexRequest request = new DeleteIndexRequest(Index);
        //es服务器执行请求 indicesClient , 请求后获得对应响应
        try {
            AcknowledgedResponse b = client.indices().delete(request, RequestOptions.DEFAULT);
            if(b.isAcknowledged()) System.out.println("是否删除成功："+b);
            else System.out.println("是否删除成功："+b);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求发生异常！！！");
        }
    }

    /**
     * 类型     -->  相当于数据表
     * 添加文档  --> 相当于数据段
     */
    public void addDoc(String index,Object doc){
        //创建请求
        IndexRequest request = new IndexRequest("vinda_index");
        //规则输入 put /vinda_index/1;
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        //将数据放入到请求  student - > json
        request.source(JSON.toJSONString(doc), XContentType.JSON);

        //客户端发送请求，获取响应结果
        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
            System.out.println("本次响应结果："+response.toString());
            System.out.println("本次执行状态："+response.status());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("本次请求出现异常");
        }
    }


    /**
     * 实际用到较多的：批量插入
     */
    public void addData(String index,List<Content> lists){
        BulkRequest bulkRequest = new BulkRequest();
        //因为大量插入数据，我们应该考虑设置的时间久一点
        bulkRequest.timeout("50S");
        //插入
        for (int i = 0; i < lists.size() ; i++) {
            bulkRequest.add(
                    new IndexRequest(index)
                            .source(JSON.toJSONString((lists.get(i))),XContentType.JSON));
        }
        //查看返回状态
        try {
            BulkResponse bulkResponse = client.bulk(bulkRequest,RequestOptions.DEFAULT);
            System.out.println(bulkResponse.hasFailures());  //是否成功
        } catch (IOException e) {
            System.out.println("批量添加失败！！！");
        }
    }

    /**
     *  查询：搜索请求，条件构造
     *  SearchRequest 搜索请求
     *  SearchSourceBuilder条件构造
     *  HighlightBuilder 构建高亮
     *  TermQueryBuilder精确查询
     *  MatchAl LQueryBuilder
     *  xxx QueryBuilder 对应我们刚才看到索引命令
     * @return
     */
    public SearchHit[] getTermQuery(String index, String name, String value){
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder sBuilder = new SearchSourceBuilder();

        //查询条件，我们可以使用QueryBuilders工具来实现
        //QueryBuilder.termQuery        精确
        //QueryBuilders.matchAllQuery() 匹配所有

        //1、查询的数据
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
        //2、装载查询条件到构造器中
        sBuilder.query(termQueryBuilder);
        //3、设置超时时 秒级
        sBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //4、发送查询请求
        searchRequest.source(sBuilder);
        //5、查看返回结果
        System.out.println("精确查询");
        try {
            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
            }
            System.out.println("查询完毕！！！");
            return searchResponse.getHits().getHits();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("返回数据有误！！！");
        }
        return new SearchHit[0];
    }

    /**
     *  matchAllQuery源码
     *  matchAllQuery的属性
     *  NAME = "match_all";
     *  ObjectParser<MatchAllQueryBuilder, Void> PARSER = new ObjectParser("match_all", MatchAllQueryBuilder::new);
     */
    public SearchHit[] getmatchAllQuery(String index) {
        System.out.println("全部查询：");
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder sBuilder = new SearchSourceBuilder();
        //1、查询所有
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //2、装载查询条件到构造器中
        sBuilder.query(matchAllQueryBuilder);
        //3、设置超时时 秒级
        sBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //4、发送查询请求
        searchRequest.source(sBuilder);
        //5、查看返回结果
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //System.out.println(JSON.toJSONString(searchResponse.getHits()));
            //遍历结果
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
            }
            System.out.println("查询完毕！！！");
            return  searchResponse.getHits().getHits();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("返回数据有误！！！");
        }
        return new SearchHit[0];

    }

}
