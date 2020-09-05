package top.weidaboy;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.weidaboy.entity.Student;
import top.weidaboy.util.ESconst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class WeidaboyApplicationTests {
    /**
     * 引用测试类
     * @Autowired 是默认根据类型匹配
     * @Qualifier 类型匹配后再找方法名，这样就确定我们找到的注入到的
     * bean是我们自定义的
     */
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 创建索引 request,面向对象来操作
     */
    @Test
    public void test01(){
        //发起创建索引请求,索引 --->  数据库 索引必须是小写哈~
        CreateIndexRequest request = new CreateIndexRequest("vinda_index");
        //es服务器执行请求 indicesClient , 请求后获得对应响应
        CreateIndexResponse response = null;
        try {
            response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("response："+response);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求发生异常！！！");
        }
    }

    /**
     * 获取索引 request
     */
    @Test
    public void test02(){
        //发起获取引请求,索引 --->  数据库 索引必须是小写哈~
        GetIndexRequest request = new GetIndexRequest("vinda_index");
        //es服务器执行请求 indicesClient , 请求后获得对应响应
        try {
            boolean b = client.indices().exists(request, RequestOptions.DEFAULT);
            if(b) System.out.println("是否存在："+b);
            else System.out.println("是否存在："+b);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求发生异常！！！");
        }
    }

    /**
     * 删除索引
     */
    @Test
    public void test03(){
        //发起删除引请求,索引 --->  数据库 索引必须是小写哈~
        DeleteIndexRequest request = new DeleteIndexRequest("vinda_index");
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
    @Test
    public void test04(){
        //创建对象
        Student student = new Student("vinda",21);
        //创建请求
        IndexRequest request = new IndexRequest("vinda_index");
        //规则输入 put /vinda_index/1;
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        //将数据放入到请求  student - > json
        request.source(JSON.toJSONString(student), XContentType.JSON);

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
        获取文档
     */
    @Test
    public void test05(){

        // get /vinda_index/1
        GetRequest request = new GetRequest("vinda_index","4");
        //不获取返回的 _source 的上下文
        request.fetchSourceContext(new FetchSourceContext((false)));

        try {
            boolean b = client.exists(request, RequestOptions.DEFAULT);
            if(b) {
                //获取文档内容
                GetResponse response = client.get(request,RequestOptions.DEFAULT);
                System.out.println(response); //获取文档内容，里面有soure 、 version等方法
                System.out.println(response.getSourceAsString()); //打印文档内容
                System.out.println("请求成功");
            }
            else System.out.println("请求失败哦");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("本次请求出现异常");
        }

    }

    /**
     更新文档
     */
    @Test
    public void test06(){
        UpdateRequest updateRequest = new UpdateRequest("vinda_index","1");
        //设置超时时间
        updateRequest.timeout("1S");
        //放入需要更新的对象
        Student student = new Student("VINDA",22);
        //更新
        updateRequest.doc(JSON.toJSONString(student),XContentType.JSON);
        //发送更新请求
        try {
            UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println("更新是否成功："+update.status());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("更新请求失败");
        }
    }

    /**
     删除文档
     */
    @Test
    public void test07(){
        DeleteRequest deleteRequest = new DeleteRequest("vinda_index","1");
        deleteRequest.timeout("1S");
        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest,RequestOptions.DEFAULT);
            System.out.println("删除是否成功："+deleteResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实际用到较多的：批量插入
     */
    @Test
    public void test08(){
        BulkRequest bulkRequest = new BulkRequest();
        //因为大量插入数据，我们应该考虑设置的时间久一点
        bulkRequest.timeout("10S");
        List<Student> students = new ArrayList<>();
        students.add(new Student("vinda1",22));
        students.add(new Student("vinda2",23));
        students.add(new Student("vinda3",24));
        students.add(new Student("weida1",25));
        students.add(new Student("weida1",25));

        //插入
        for (int i = 0; i < students.size() ; i++) {
            bulkRequest.add(
                    new IndexRequest("vinda_index").id(""+(i+1))
                    .source(JSON.toJSONString((students.get(i))),XContentType.JSON));

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
     */
    @Test
    public void test09(){
        SearchRequest searchRequest = new SearchRequest(ESconst.ES_INDEX);
        //构建搜索条件
        SearchSourceBuilder sBuilder = new SearchSourceBuilder();

        //查询条件，我们可以使用QueryBuilders工具来实现
        //QueryBuilder.termQuery        精确
        //QueryBuilders.matchAllQuery() 匹配所有

        //1、查询sname = weida的数据
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("sname", "weida1");
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
            //System.out.println(JSON.toJSONString(searchResponse.getHits()));
            //遍历结果
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
            }
            System.out.println("查询完毕！！！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("返回数据有误！！！");
        }

        /**
         *  matchAllQuery源码
         *  matchAllQuery的属性
         *  NAME = "match_all";
         *  ObjectParser<MatchAllQueryBuilder, Void> PARSER = new ObjectParser("match_all", MatchAllQueryBuilder::new);
         */
        System.out.println("全部查询：");
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
            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            //System.out.println(JSON.toJSONString(searchResponse.getHits()));
            //遍历结果
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
            }
            System.out.println("查询完毕！！！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("返回数据有误！！！");
        }
    }

    @Test
    void contextLoads() {


    }

}
