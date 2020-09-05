package top.weidaboy.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //相当于xml
public class ElasticSearchClientConfig {

    /**
     *   spring <bean id="restHighLevelClient" class = "RestHighLevelClient">
     */

    @Bean
    public RestHighLevelClient restHighLevelClient(){
//      RestHighLevelClient:高级客户端
        RestHighLevelClient client = new RestHighLevelClient(
                //这里我就演示一个连接
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
//                     new HttpHost("localhost", 9201, "http")));
        return  client;
    }
}
